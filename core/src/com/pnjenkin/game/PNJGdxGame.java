package com.pnjenkin.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;

public class PNJGdxGame extends ApplicationAdapter {
	// REM out all boilerplate code - class variables and inside methods
//	SpriteBatch batch;
//	Texture img;
	private SpriteBatch spriteBatch;
	private FreeTypeFontGenerator freeTypeFontGenerator;
	private BitmapFont bitmapFont;
	FreeTypeFontGenerator.FreeTypeFontParameter freeTypeFontParameter;
	private OrthographicCamera orthographicCamera;


	@Override
	public void create () {
//		batch = new SpriteBatch();
//		img = new Texture("badlogic.jpg");
//		freeTypeFontGenerator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/arial.ttf"));
		freeTypeFontGenerator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/timesnewroman.ttf"));
		// add ttf file manually in new fonts within android/assets
		freeTypeFontParameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
		freeTypeFontParameter.size = 72;
		freeTypeFontParameter.color = Color.RED;
//		freeTypeFontParameter.characters = "Hello there";
		bitmapFont = freeTypeFontGenerator.generateFont(freeTypeFontParameter);
		// use freetype font https://stackoverflow.com/a/40739429/11365317
		// best to specify this from start - very hard to retrospectively add this library

//		bitmapFont = new BitmapFont();
//		bitmapFont.setColor(Color.BLACK); // this is deprecated, should by 2019 be using FreeType, but...
//		bitmapFont.getData().setScale(5);	// default font size small on hi-res screen, so scale-up

		// NB Screen expected to be in landscape format by libGDX by default https://stackoverflow.com/a/35614010/11365317

	}

	@Override
	public void render () {
//		Gdx.gl.glClearColor(1, 0, 0, 1);
//		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
//		batch.begin();
//		batch.draw(img, 0, 0);
//		batch.end();

		// Wipe the screen (always done) a certain RGB colour and alpha
		Gdx.gl.glClearColor(1,1,1,1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);		// clear, using parameters in colour

		orthographicCamera = new OrthographicCamera();
		orthographicCamera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		orthographicCamera.update();

		spriteBatch = new SpriteBatch();
		spriteBatch.setProjectionMatrix(orthographicCamera.combined);
		// Drawing happening below
		spriteBatch.begin();
		bitmapFont.draw(spriteBatch, "Durdadhewhi, fatla genowgh whi?", 0,100);
		// Origin of screen is bottom-left, so a y of zero is likely to be off-screen/invisible
		spriteBatch.end();

	}
	
	@Override
	public void dispose () {
//		batch.dispose();
//		img.dispose();
		spriteBatch.dispose();
		bitmapFont.dispose();
		freeTypeFontGenerator.dispose();
	}
}
