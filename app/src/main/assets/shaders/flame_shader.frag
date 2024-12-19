precision mediump float;
uniform sampler2D texture;
uniform float uTime;
varying vec2 fragTextureCord;

void main() {
    float waveX = 0.03 * sin(uTime * 3.0 + fragTextureCord.y * 15.0);
    float waveY = 0.03 * cos(uTime * 2.5 + fragTextureCord.x * 10.0);
    vec2 movedTexCoord = fragTextureCord + vec2(waveX, waveY);

    vec4 color = texture2D(texture, movedTexCoord);

    float intensity = 0.7 + 0.3 * sin(uTime * 4.0 + fragTextureCord.y * 20.0);
    gl_FragColor = vec4(color.rgb, color.a * intensity);
}