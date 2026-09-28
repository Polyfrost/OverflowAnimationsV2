#version 330
//? if >=26.3 {
#extension GL_ARB_separate_shader_objects : require
//?}

//? if <26.3 {
//#moj_import <minecraft:dynamictransforms.glsl>
//?} else {
#include <minecraft:dynamictransforms.glsl>
//?}

uniform samplerCube Sampler0;

//? if <26.3 {
//in vec3 texCoord0;
//?} else {
layout(location = 0) in vec3 texCoord0;
//?}

//? if <26.3 {
//out vec4 fragColor;
//?} else {
layout(location = 0) out vec4 fragColor;
//?}

void main() {
    fragColor = texture(Sampler0, texCoord0) * ColorModulator;
}