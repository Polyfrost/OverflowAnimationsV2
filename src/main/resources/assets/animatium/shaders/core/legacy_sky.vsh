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

//? if <26.3 {
//in vec3 Position;
//?} else {
layout(location = 0) in vec3 Position;
//?}

//? if <26.3 {
//out float cylindricalVertexDistance;
//out float sphericalVertexDistance;
//?} else {
layout(location = 0) out float cylindricalVertexDistance;
layout(location = 1) out float sphericalVertexDistance;
//?}

void main() {
    vec4 eye = ModelViewMat * vec4(Position, 1.0);
#ifdef PLANAR_FOG
    float dist = abs(eye.z);
    cylindricalVertexDistance = dist;
    sphericalVertexDistance = dist;
#else
    cylindricalVertexDistance = fog_cylindrical_distance(Position);
    sphericalVertexDistance = fog_spherical_distance(Position);
#endif
    gl_Position = ProjMat * eye;
}
