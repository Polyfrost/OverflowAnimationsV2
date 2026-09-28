#version 330
//? if >=26.3 {
#extension GL_ARB_separate_shader_objects : require
//?}

//? if <26.3 {
//#moj_import <minecraft:fog.glsl>
//#moj_import <minecraft:dynamictransforms.glsl>
//?} else {
#include <minecraft:fog.glsl>
#include <minecraft:dynamictransforms.glsl>
//?}

//? if <26.3 {
//in float cylindricalVertexDistance;
//in float sphericalVertexDistance;
//?} else {
layout(location = 0) in float cylindricalVertexDistance;
layout(location = 1) in float sphericalVertexDistance;
//?}

//? if <26.3 {
//out vec4 fragColor;
//?} else {
layout(location = 0) out vec4 fragColor;
//?}

void main() {
    fragColor = apply_fog(ColorModulator, sphericalVertexDistance, cylindricalVertexDistance, 0.0, FogSkyEnd, FogSkyEnd, FogSkyEnd, FogColor);
}
