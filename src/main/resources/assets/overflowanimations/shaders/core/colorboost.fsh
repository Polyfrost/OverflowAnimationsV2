#version 330
//? if >=26.3 {
#extension GL_ARB_separate_shader_objects : require
//?}

uniform sampler2D Sampler0;
//? if <26.3 {
//in vec2 texCoord;
//out vec4 fragColor;
//?} else {
layout(location = 0) in vec2 texCoord;
layout(location = 0) out vec4 fragColor;
//?}

void main() {
    vec4 color = texture(Sampler0, texCoord);
	color.r = 1.34 * color.r - 0.1 * (color.g + color.b);
	color.g = 1.2 * color.g - 0.1 * (color.r + color.b);
	color.b = 1.1 * color.b - 0.1 * (color.r + color.g);
	color.rgb = 3.0 * color.rgb / (color.rgb + 2.2);
    fragColor = color;
}
