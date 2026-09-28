#version 330
//? if >=26.3 {
#extension GL_ARB_separate_shader_objects : require
//?}

//? if <26.3 {
//#moj_import <minecraft:dynamictransforms.glsl>
//#moj_import <minecraft:projection.glsl>
//?} else {
#include <minecraft:dynamictransforms.glsl>
#include <minecraft:projection.glsl>
//?}

//? if <26.3 {
//in vec3 Position;
//in vec2 UV0;
//?} else {
layout(location = 0) in vec3 Position;
layout(location = 1) in vec2 UV0;
//?}

//? if <26.3 {
//out vec2 texCoord0;
//?} else {
layout(location = 0) out vec2 texCoord0;
//?}

void main() {
    gl_Position = ProjMat * ModelViewMat * vec4(Position, 1.0);
    texCoord0 = UV0;
}