#version 330
//? if >=26.3 {
#extension GL_ARB_separate_shader_objects : require
//?}

//? if <26.3 {
//#moj_import <minecraft:fog.glsl>
//?} else {
#include <minecraft:fog.glsl>
//?}

//? if <26.3 {
//in float cylindricalVertexDistance;
//in float sphericalVertexDistance;
//in vec4 vertexColor;
//?} else {
layout(location = 0) in float cylindricalVertexDistance;
layout(location = 1) in float sphericalVertexDistance;
layout(location = 2) in vec4 vertexColor;
//?}

//? if <26.3 {
//out vec4 fragColor;
//?} else {
layout(location = 0) out vec4 fragColor;
//?}

void main() {
    fragColor = apply_fog(vertexColor, sphericalVertexDistance, cylindricalVertexDistance, 0.0, FogCloudsEnd, FogCloudsEnd, FogCloudsEnd, FogColor);
}