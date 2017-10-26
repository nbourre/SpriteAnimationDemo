package com.nicolasbourre.spriteanimationdemo;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class SpriteAnimationDemo extends ApplicationAdapter {
	SpriteBatch batch;
	Texture img;

	int frame_rate = 30;
	float refresh_rate;
	float refresh_acc = 0;
	float deltaTime;

	float stateTime = 0.0f;

	private static final int FRAME_COLS = 6;
	private static final int FRAME_ROWS = 5;

	Animation walkAnimation;
	Texture walkSheet;
	TextureRegion[] walkFrames;
	TextureRegion currentFrame;

	@Override
	public void create () {
		refresh_rate = 1.0f/frame_rate;

		batch = new SpriteBatch();
		walkSheet = new Texture("animation_sheet.png");

		TextureRegion [][] temp = TextureRegion.split(walkSheet,
				walkSheet.getWidth() / FRAME_COLS,
				walkSheet.getHeight() / FRAME_ROWS);

		walkFrames = new TextureRegion[FRAME_COLS * FRAME_ROWS];

		int index = 0;

		for (int j = 0; j < FRAME_ROWS; j++) {
			for (int i = 0; i < FRAME_COLS; i++) {
				walkFrames[index++] = temp[j][i];
			}
		}

		walkAnimation = new Animation(0.025f, walkFrames);
		stateTime = 0.0f;

	}

	@Override
	public void render () {
		deltaTime = Gdx.graphics.getDeltaTime();
		refresh_acc += deltaTime;

		update(deltaTime);
		draw();
	}

	public void update(float delta){
		if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
			Gdx.app.exit();
		}

		stateTime += deltaTime;
		currentFrame = (TextureRegion) walkAnimation.getKeyFrame(stateTime, true);
	}

	public void draw() {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);


		batch.begin();
		batch.draw(currentFrame, 50, 50);
		batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		walkSheet.dispose();
	}
}
