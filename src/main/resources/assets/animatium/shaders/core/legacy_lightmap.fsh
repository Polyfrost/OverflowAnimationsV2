#version 330
//? if >=26.3 {
#extension GL_ARB_separate_shader_objects : require
//?}

precision highp float;

layout(std140) uniform LightmapInfo {
    float SkyDarken;
    float SkyDarkness;
    float BlockLightRed;
    float NightVisionScale;
    float Gamma;
    int UseBrightLightmap;
};

//? if <26.3 {
//in vec2 texCoord;
//out vec4 fragColor;
//?} else {
layout(location = 0) in vec2 texCoord;
layout(location = 0) out vec4 fragColor;
//?}

float getBrightness(int index) {
    float value = 1.0 - float(index) / 15.0;
    return (1.0 - value) / (value * 3.0 + 1.0);
}

vec3 notGamma(vec3 value) {
    vec3 inverted = 1.0 - value;
    return 1.0 - inverted * inverted * inverted * inverted;
}

void main() {
    float skyLight = getBrightness(min(int(texCoord.y * 16), 15)) * (SkyDarken * 0.95F + 0.05F);
    float blockLight = getBrightness(min(int(texCoord.x * 16), 15)) * (BlockLightRed * 0.1 + 1.5);

    float skyV = skyLight * (SkyDarken * 0.65 + 0.35);
    float blockVA = blockLight * ((blockLight * 0.6 + 0.4) * 0.6 + 0.4);
    float blockVB = blockLight * (blockLight * blockLight * 0.6 + 0.4);

    vec3 color = vec3(skyV + blockLight, skyV + blockVA, skyLight + blockVB);
    color *= 0.96;
    color += 0.03;
    if (SkyDarkness > 0.0) {
        color = mix(color, color * vec3(0.7, 0.6, 0.6), SkyDarkness);
    }

    if (UseBrightLightmap == 1) {
        color = vec3(0.22 + blockLight * 0.75, 0.28 + blockVA * 0.75, 0.25 + blockVB * 0.75);
    }

    if (NightVisionScale > 0.0) {
        color *= mix(1.0, 1.0 / min(min(color.r, color.g), color.b), NightVisionScale);
    }

    color = min(color, 1.0);
    color = mix(color, notGamma(color), Gamma);
    color = mix(color, vec3(0.75), 0.04);
    color = clamp(color, 0.0, 1.0);
    color *= 255.0;
    fragColor = vec4(vec3(ivec3(color) / 255.0), 1.0);
}
