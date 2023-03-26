package doggytalents.client.entity.skin;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.google.common.collect.Maps;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.util.Mth;

public class DoggyJemParser {

    public static List<String> REQUIRED_PART = List.of("head", "body", "mane", 
        "leg1", "leg2", "leg3", "leg4", "tail");

    public static Map<String, String> SUBSTITUTE_ID;
    static {
        SUBSTITUTE_ID = Maps.newConcurrentMap();
        SUBSTITUTE_ID.put("leg1", "left_hind_leg");
        SUBSTITUTE_ID.put("leg2", "right_hind_leg");
        SUBSTITUTE_ID.put("leg3", "left_front_leg");
        SUBSTITUTE_ID.put("leg4", "right_front_leg");
        SUBSTITUTE_ID.put("mane", "upper_body");
    }
    
    public static LayerDefinition parseJem(JsonObject jemObject) throws Exception {
        var txtSize = jemObject.getAsJsonArray("textureSize");
        if (txtSize == null || txtSize.size() < 2) throw new IllegalArgumentException();

        var meshDefinition = new MeshDefinition();
        var rootPart = meshDefinition.getRoot();

        var modelArr = jemObject.getAsJsonArray("models");
        var missing = checkRequiredPartAtRootAndReturnMissing(modelArr);
        if (!missing.isEmpty()) 
            throw new IllegalArgumentException("Missing parts : " + missing.toString());
        parseModelArrayAndAddToDefinitionAtRoot(rootPart, modelArr);

        var ret = LayerDefinition.create(meshDefinition, 
            txtSize.get(0).getAsInt(), txtSize.get(1).getAsInt());

        return ret;
    }

    public static List<String> checkRequiredPartAtRootAndReturnMissing(JsonArray modelArray) {
        var requiredLeft = new ArrayList<String>(REQUIRED_PART);
        for (var e : modelArray) {
            var model = e.getAsJsonObject();
            var id = model.get("part");
            if (id == null) continue;
            var idStr = id.getAsString();
            if (requiredLeft.contains(idStr)) {
                requiredLeft.remove(idStr);
            }
        }
        return requiredLeft;
    }

    public static void parseModelArrayAndAddToDefinitionAtRoot(PartDefinition root, JsonArray modelArray) {
        for (var element : modelArray) {
            var jsonObject = element.getAsJsonObject();
            var addedPart = parseModelAndAddToDefinition(root, jsonObject, true);
            var submodels = jsonObject.get("submodels");
            if (submodels != null && addedPart != null) {
                parseModelArrayAndAddToDefinition(addedPart, submodels.getAsJsonArray());
            }
        }

    }

    public static void parseModelArrayAndAddToDefinition(PartDefinition part, JsonArray modelArray) {
        for (var element : modelArray) {
            var jsonObject = element.getAsJsonObject();
            var addedPart = parseModelAndAddToDefinition(part, jsonObject, false);
            var submodels = jsonObject.get("submodels");
            if (submodels != null && addedPart != null) {
                parseModelArrayAndAddToDefinition(addedPart, submodels.getAsJsonArray());
            }
        }

    }

    public static PartDefinition parseModelAndAddToDefinition(PartDefinition part, JsonObject model, boolean isRoot) {
        var id = model.get(isRoot ? "part" : "id");
        if (id == null) return null;
        var idStr = id.getAsString();
        if (isRoot) {
            if (!REQUIRED_PART.contains(idStr)) return null;
            if (SUBSTITUTE_ID.containsKey(idStr)) {
                idStr = SUBSTITUTE_ID.get(idStr);
            }
        }
        var offset = model.get("translate");
        if (offset == null) return null;
        var offsetArr = offset.getAsJsonArray();
        float[] offsetFloatArr = {
            offsetArr.get(0).getAsFloat(),
            offsetArr.get(1).getAsFloat(),
            offsetArr.get(2).getAsFloat()
        };
        if (isRoot) {
            for (int i = 0; i < 3; ++i) offsetFloatArr[i] = -offsetFloatArr[i];
        }
        offsetFloatArr[0] = -offsetFloatArr[0];
        offsetFloatArr[1] = -offsetFloatArr[1];

        var cubeList = CubeListBuilder.create();
        if (isRoot) {
            parseModelAndAddCubesAtRoot(cubeList, model, offsetFloatArr);
        } else {
            parseModelAndAddCubes(cubeList, model);
        }
        float[] rotationFloatArr = {0, 0, 0};
        var rotation = model.get("rotate");
        if (rotation != null) {
            var rotationArr = rotation.getAsJsonArray();
            rotationFloatArr[0] = 
                -(rotationArr.get(0).getAsFloat())*Mth.DEG_TO_RAD;
            rotationFloatArr[1] =
                -(rotationArr.get(1).getAsFloat())*Mth.DEG_TO_RAD;
            rotationFloatArr[2] = 
                rotationArr.get(2).getAsFloat()*Mth.DEG_TO_RAD;
        }

        if (isRoot) {
            var submodel = model.get("submodels");
            if (submodel != null) {
                fixRootSubModelsOffset(submodel.getAsJsonArray(), offsetFloatArr);
            }
        }

        if (isRoot) {
            offsetFloatArr[1] += 24;
            if (idStr.equals("head")) {
                var ret = part.addOrReplaceChild("head", CubeListBuilder.create(), 
                    PartPose.offset(offsetFloatArr[0], offsetFloatArr[1], offsetFloatArr[2]));
                var real_head = ret.addOrReplaceChild("real_head", cubeList, 
                PartPose.offsetAndRotation(0, 0, 0, 
                    rotationFloatArr[0], rotationFloatArr[1], rotationFloatArr[2]));
                real_head.addOrReplaceChild("ear_normal", CubeListBuilder.create(),PartPose.ZERO);
                real_head.addOrReplaceChild("ear_boni", CubeListBuilder.create(),PartPose.ZERO);
                real_head.addOrReplaceChild("ear_small", CubeListBuilder.create(),PartPose.ZERO);
                return ret;
            }
            if (idStr.equals("tail")) {
                var ret = part.addOrReplaceChild("tail", CubeListBuilder.create(), 
                    PartPose.offset(offsetFloatArr[0], offsetFloatArr[1], offsetFloatArr[2]));
                ret.addOrReplaceChild("real_tail", cubeList, 
                    PartPose.offsetAndRotation(0, 0, 0, 
                    rotationFloatArr[0], rotationFloatArr[1], rotationFloatArr[2]));
                ret.addOrReplaceChild("real_tail_2", CubeListBuilder.create(), PartPose.ZERO);
                ret.addOrReplaceChild("real_tail_bushy", CubeListBuilder.create(), PartPose.ZERO);
                return ret;
            }
        }

        var ret = part.addOrReplaceChild(idStr, cubeList, 
            PartPose.offsetAndRotation(offsetFloatArr[0], offsetFloatArr[1], offsetFloatArr[2], 
                rotationFloatArr[0], rotationFloatArr[1], rotationFloatArr[2]));
        return ret;
    }

    public static void fixRootSubModelsOffset(JsonArray modelArray, float[] rootOffset) {
        for (var e : modelArray) {
            var model = e.getAsJsonObject();
            var offset = model.get("translate");
            if (offset == null) continue;
            var offsetArr = offset.getAsJsonArray();
            float[] offsetFloatArr = {
                offsetArr.get(0).getAsFloat(),
                offsetArr.get(1).getAsFloat(),
                offsetArr.get(2).getAsFloat()
            };
            offsetFloatArr[0] = -offsetFloatArr[0] - rootOffset[0];
            offsetFloatArr[1] = -offsetFloatArr[1] - rootOffset[1];
            offsetFloatArr[2] -= rootOffset[2];
            var newOffsetArr = new JsonArray(3);
            //Re convert to jem coord sys
            newOffsetArr.add(-offsetFloatArr[0]);
            newOffsetArr.add(-offsetFloatArr[1]);
            newOffsetArr.add(offsetFloatArr[2]);
            model.add("translate", newOffsetArr);
        }
    }

    public static void parseModelAndAddCubes(CubeListBuilder cubeList, JsonObject model) {
        var cubes = model.get("boxes");
        if (cubes == null) return;
        var mirrorText = model.get("mirrorTexture");
        if (mirrorText != null) {
            //All mirror cubes
            parseModelAndAddCubeMirrored(cubeList, model);
            return;
        } 
        var cubesArr = cubes.getAsJsonArray();
        for (var e : cubesArr) {
            var cube = e.getAsJsonObject();
            var cubeDim = cube.get("coordinates");
            if (cubeDim == null) continue;
            var cubeTex = cube.get("textureOffset");
            if (cubeTex == null) continue;
            var sizeAdd = cube.get("sizeAdd");
            float sizeAddFloat = 0F;
            if (sizeAdd != null) {
                sizeAddFloat = sizeAdd.getAsFloat();
            }
            var cubeDimArr = cubeDim.getAsJsonArray();
            float[] cubeFloat = new float[6];
            for (int i = 0; i < 6; ++i) 
                cubeFloat[i] = cubeDimArr.get(i).getAsFloat();
            cubeFloat[0] = -cubeFloat[0];
            cubeFloat[1] = -cubeFloat[1];
            cubeFloat[0] -= cubeFloat[3];
            cubeFloat[1] -= cubeFloat[4];
            var cubeTexArr = cubeTex.getAsJsonArray();
            int[] cubeTextInt = new int[2];
            for (int i = 0; i < 2; ++i) 
                cubeTextInt[i] = cubeTexArr.get(i).getAsInt();
            cubeList.texOffs(cubeTextInt[0], cubeTextInt[1])
                .addBox(cubeFloat[0], cubeFloat[1], cubeFloat[2],
                    cubeFloat[3], cubeFloat[4], cubeFloat[5], sizeAddFloat == 0 ? CubeDeformation.NONE 
                    : new CubeDeformation(sizeAddFloat));
        }

        //Add mirroed cubes
        var submodels = model.get("submodels");
        if (submodels == null) return;
        var submodelsArr = submodels.getAsJsonArray();
        for (var e : submodelsArr) {
            var submodel = e.getAsJsonObject();
            if (submodel.get("id") != null) continue;
            var mirror = submodel.get("mirrorTexture");
            if (mirror == null) continue;
            parseModelAndAddCubeMirrored(cubeList, submodel);
        }
        
    }

    public static void parseModelAndAddCubeMirrored(CubeListBuilder cubeList, JsonObject model) {
        var cubes = model.get("boxes");
        if (cubes == null) return;
        var cubesArr = cubes.getAsJsonArray();
        for (var e : cubesArr) {
            var cube = e.getAsJsonObject();
            var cubeDim = cube.get("coordinates");
            if (cubeDim == null) continue;
            var cubeTex = cube.get("textureOffset");
            if (cubeTex == null) continue;
            var sizeAdd = cube.get("sizeAdd");
            float sizeAddFloat = 0F;
            if (sizeAdd != null) {
                sizeAddFloat = sizeAdd.getAsFloat();
            }
            var cubeDimArr = cubeDim.getAsJsonArray();
            float[] cubeFloat = new float[6];
            for (int i = 0; i < 6; ++i) 
                cubeFloat[i] = cubeDimArr.get(i).getAsFloat();
            cubeFloat[0] = -cubeFloat[0];
            cubeFloat[1] = -cubeFloat[1];
            cubeFloat[0] -= cubeFloat[3];
            cubeFloat[1] -= cubeFloat[4];
            var cubeTexArr = cubeTex.getAsJsonArray();
            int[] cubeTextInt = new int[2];
            for (int i = 0; i < 2; ++i) 
                cubeTextInt[i] = cubeTexArr.get(i).getAsInt();
            cubeList.texOffs(cubeTextInt[0], cubeTextInt[1]).mirror()
                .addBox(cubeFloat[0], cubeFloat[1], cubeFloat[2],
                    cubeFloat[3], cubeFloat[4], cubeFloat[5], sizeAddFloat == 0 ? CubeDeformation.NONE 
                    : new CubeDeformation(sizeAddFloat)).mirror(false);
        }
    }

    

    public static void parseModelAndAddCubesAtRoot(CubeListBuilder cubeList, JsonObject model, float[] rootOffset) {
        var cubes = model.get("boxes");
        if (cubes == null) return;
        var mirrorText = model.get("mirrorTexture");
        if (mirrorText != null) {
            //All mirror cubes
            parseModelAndAddCubesMirroredAtRoot(cubeList, model, rootOffset);
            
            return;
        } 
        var cubesArr = cubes.getAsJsonArray();
        for (var e : cubesArr) {
            var cube = e.getAsJsonObject();
            var cubeDim = cube.get("coordinates");
            if (cubeDim == null) continue;
            var cubeTex = cube.get("textureOffset");
            if (cubeTex == null) continue;
            var sizeAdd = cube.get("sizeAdd");
            float sizeAddFloat = 0F;
            if (sizeAdd != null) {
                sizeAddFloat = sizeAdd.getAsFloat();
            }
            var cubeDimArr = cubeDim.getAsJsonArray();
            float[] cubeFloat = new float[6];
            for (int i = 0; i < 6; ++i) 
                cubeFloat[i] = cubeDimArr.get(i).getAsFloat();
            cubeFloat[0] = -cubeFloat[0];
            cubeFloat[1] = -cubeFloat[1];
            cubeFloat[0] = cubeFloat[0] -rootOffset[0];
            cubeFloat[1] = cubeFloat[1] -rootOffset[1];
            cubeFloat[2] = cubeFloat[2] -rootOffset[2];
            cubeFloat[0] -= cubeFloat[3];
            cubeFloat[1] -= cubeFloat[4];
            var cubeTexArr = cubeTex.getAsJsonArray();
            int[] cubeTextInt = new int[2];
            for (int i = 0; i < 2; ++i) 
                cubeTextInt[i] = cubeTexArr.get(i).getAsInt();
            cubeList.texOffs(cubeTextInt[0], cubeTextInt[1])
                .addBox(cubeFloat[0], cubeFloat[1], cubeFloat[2],
                    cubeFloat[3], cubeFloat[4], cubeFloat[5], sizeAddFloat == 0 ? CubeDeformation.NONE 
                    : new CubeDeformation(sizeAddFloat));
        }

        var submodels = model.get("submodels");
        if (submodels == null) return;
        var submodelsArr = submodels.getAsJsonArray();
        for (var e : submodelsArr) {
            var submodel = e.getAsJsonObject();
            if (submodel.get("id") != null) continue;
            var mirror = submodel.get("mirrorTexture");
            if (mirror == null) continue;
            parseModelAndAddCubesMirroredAtRoot(cubeList, submodel, rootOffset);
        }
    }

    public static void parseModelAndAddCubesMirroredAtRoot(CubeListBuilder cubeList, JsonObject model, float[] rootOffset) {
        var cubes = model.get("boxes");
        if (cubes == null) return;
        var cubesArr = cubes.getAsJsonArray();
        for (var e : cubesArr) {
            var cube = e.getAsJsonObject();
            var cubeDim = cube.get("coordinates");
            if (cubeDim == null) continue;
            var cubeTex = cube.get("textureOffset");
            if (cubeTex == null) continue;
            var sizeAdd = cube.get("sizeAdd");
            float sizeAddFloat = 0F;
            if (sizeAdd != null) {
                sizeAddFloat = sizeAdd.getAsFloat();
            }
            var cubeDimArr = cubeDim.getAsJsonArray();
            float[] cubeFloat = new float[6];
            for (int i = 0; i < 6; ++i) 
                cubeFloat[i] = cubeDimArr.get(i).getAsFloat();
            cubeFloat[0] = -cubeFloat[0];
            cubeFloat[1] = -cubeFloat[1];
            cubeFloat[0] = cubeFloat[0] -rootOffset[0];
            cubeFloat[1] = cubeFloat[1] -rootOffset[1];
            cubeFloat[2] = cubeFloat[2] -rootOffset[2];
            cubeFloat[0] -= cubeFloat[3];
            cubeFloat[1] -= cubeFloat[4];
            var cubeTexArr = cubeTex.getAsJsonArray();
            int[] cubeTextInt = new int[2];
            for (int i = 0; i < 2; ++i) 
                cubeTextInt[i] = cubeTexArr.get(i).getAsInt();
            cubeList.texOffs(cubeTextInt[0], cubeTextInt[1]).mirror()
                .addBox(cubeFloat[0], cubeFloat[1], cubeFloat[2],
                    cubeFloat[3], cubeFloat[4], cubeFloat[5], sizeAddFloat == 0 ? 
                    CubeDeformation.NONE 
                    : new CubeDeformation(sizeAddFloat)).mirror(false);
        }
    }

}
