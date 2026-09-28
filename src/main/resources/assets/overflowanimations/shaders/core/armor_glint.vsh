#version 330
//? if >=26.3 {
#extension GL_ARB_separate_shader_objects : require
//?}

//? if <26.3 {
//#moj_import <minecraft:fog.glsl>
//#moj_import <minecraft:dynamictransforms.glsl>
//#moj_import <minecraft:projection.glsl>
//?} else {
#include <minecraft:fog.glsl>
#include <minecraft:dynamictransforms.glsl>
#include <minecraft:projection.glsl>
//?}

uniform sampler2D Sampler1;

//? if <26.3 {
//in vec3 Position;
//in vec2 UV0;
//in ivec2 UV1;
//?} else {
layout(location = 0) in vec3 Position;
layout(location = 1) in vec2 UV0;
layout(location = 2) in ivec2 UV1;
//?}

//? if <26.3 {
//out float sphericalVertexDistance;
//out float cylindricalVertexDistance;
//out vec2 texCoord0;
//out vec4 overlayColor;
//?} else {
layout(location = 0) out float sphericalVertexDistance;
layout(location = 1) out float cylindricalVertexDistance;
layout(location = 2) out vec2 texCoord0;
layout(location = 3) out vec4 overlayColor;
//?}

// Shader source from 26.2 glint.vsh modified
void main() {
    gl_Position = ProjMat * ModelViewMat * vec4(Position, 1.0);
    sphericalVertexDistance = fog_spherical_distance(Position);
    cylindricalVertexDistance = fog_cylindrical_distance(Position);
    texCoord0 = (TextureMat * vec4(UV0, 0.0, 1.0)).xy;
    overlayColor = texelFetch(Sampler1, UV1, 0);
}