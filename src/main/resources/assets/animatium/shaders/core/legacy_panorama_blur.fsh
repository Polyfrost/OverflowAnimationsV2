#version 330
//? if >=26.3 {
#extension GL_ARB_separate_shader_objects : require
//?}

uniform sampler2D Sampler0;

//? if <26.3 {
//in vec2 texCoord0;
//?} else {
layout(location = 0) in vec2 texCoord0;
//?}

//? if <26.3 {
//out vec4 fragColor;
//?} else {
layout(location = 0) out vec4 fragColor;
//?}

void main() {
    vec4 color = vec4(0.0);
    for (int cycle = 0; cycle < 3; cycle++) {
        float alpha = 1.0 / float(cycle + 1);
        float growth = (float(cycle) - 1.5) / 256.0;
        vec4 sampleColor = texture(Sampler0, texCoord0 + vec2(growth, 0.0));
        color = mix(color, sampleColor, alpha);
    }

    fragColor = color;
}