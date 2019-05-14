package com.pnjenkin.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;

import javax.xml.soap.Text;

public class PNJGdxGame extends ApplicationAdapter implements InputProcessor {
	// REM out all boilerplate code - class variables and inside methods
//	SpriteBatch batch;
//	Texture img;
	private SpriteBatch spriteBatch;
	private FreeTypeFontGenerator freeTypeFontGenerator;
	private BitmapFont bitmapFont;
	FreeTypeFontGenerator.FreeTypeFontParameter freeTypeFontParameter;
	private OrthographicCamera orthographicCamera;
	private Texture image;		// raw image
	private Sprite sprite;
	// for animation (74)
	private TextureAtlas shooterAtlas;
//	private Animation animation;
	private Animation<TextureRegion> animation;
	private float timePassed = 0;		// used to determine where in sequence
	// for handling user input (75)
	private int screenWidth, screenHeight;
	private String message = "Touch to activate";
	GlyphLayout glyphLayout;

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
		bitmapFont = freeTypeFontGenerator.generateFont(freeTypeFontParameter);
		// use freetype font https://stackoverflow.com/a/40739429/11365317
		// best to specify this from start - very hard to retrospectively add this library

//		bitmapFont = new BitmapFont();
//		bitmapFont.setColor(Color.BLACK); // this is deprecated, should by 2019 be using FreeType, but...
//		bitmapFont.getData().setScale(5);	// default font size small on hi-res screen, so scale-up

		// NB Screen expected to be in landscape format by libGDX by default https://stackoverflow.com/a/35614010/11365317
		image = new Texture("v_jenkin_organ_cbvmc.jpg");		// raw image data
//		image = new Texture("vjorgansmall.png");		// raw image data

		// 74 animation
		shooterAtlas = new TextureAtlas(Gdx.files.internal("shooter_dude.pack"));
//		animation = new Animation(1/30f, shooterAtlas.getRegions());
		animation = new Animation<TextureRegion>(1/30f, shooterAtlas.getRegions());
		// 1/30f for 30 fps

		// 75 handling user input
		screenWidth = Gdx.graphics.getWidth();
		screenHeight = Gdx.graphics.getHeight();

		Gdx.input.setInputProcessor(this);		// look in this class itself for input methods

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

		// for use with user input
		glyphLayout = new GlyphLayout();		// getBounds deprecated, so use GlypthLayout
		glyphLayout.setText(bitmapFont, message);
		float textWidth = glyphLayout.width;
		float textHeight = glyphLayout.height;

		float x = screenWidth / 2 - textWidth /2;
		float y = screenHeight / 2 - textHeight /2;

		Sprite sprite = new Sprite(image);	// initialise sprite

		spriteBatch = new SpriteBatch();
//		spriteBatch.setProjectionMatrix(orthographicCamera.combined);
		// Drawing happening below
		spriteBatch.begin();
		// for 71 text
//		bitmapFont.draw(spriteBatch, "Durdadhewhi, fatla genowgh whi?", 0,100);
		// Origin of screen is bottom-left, so a y of zero is likely to be off-screen/invisible
		// for 73 draw image
//		sprite.draw(spriteBatch);
		// for 74 animation
//		timePassed += Gdx.graphics.getDeltaTime();		// use gdx library for time
//		spriteBatch.draw(animation.getKeyFrame(timePassed, true), 100, 100);
		// for 75 handling user input
		bitmapFont.draw(spriteBatch,message, x, y);		// calculated to draw centred in screen
		spriteBatch.end();
	}
	
	@Override
	public void dispose () {
//		batch.dispose();
//		img.dispose();
		spriteBatch.dispose();
		bitmapFont.dispose();
		freeTypeFontGenerator.dispose();
		image.dispose();		// remember to dispose of everything!
		shooterAtlas.dispose();		// remember to dispose of everything! (anything implementing Disposable)
	}

	// manually move touch events to top here - since focussing on mobile device (not desktop) only
	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		message = "Touched down at " + screenX + ", " + screenY;
		return true;
//		return false;
	}

	/**
	 * Called when a finger was lifted or a mouse button was released. The button parameter will be {link Buttons#LEFT} on iOS.
	 *
	 * @param screenX
	 * @param screenY
	 * @param pointer the pointer for the event.
	 * @param button  the button
	 * @return whether the input was processed
	 */
	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		message = "Touched up at " + screenX + ", " + screenY;
		return true;
//		return false;
	}

	/**
	 * Called when a finger or the mouse was dragged.
	 *
	 * @param screenX
	 * @param screenY
	 * @param pointer the pointer for the event.
	 * @return whether the input was processed
	 */
	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		// touchDragged fired a lot! by accidental dragging, so don't really implement touchDragged at the mo
		return false;
	}

// end of touch events


	/**
	 * Called when a key was pressed
	 *
	 * @param keycode one of the constants in {link Input.Keys}
	 * @return whether the input was processed
	 */
	@Override
	public boolean keyDown(int keycode) {
		return false;
	}

	/**
	 * Called when a key was released
	 *
	 * @param keycode one of the constants in {link Input.Keys}
	 * @return whether the input was processed
	 */
	@Override
	public boolean keyUp(int keycode) {
		return false;
	}

	/**
	 * Called when a key was typed
	 *
	 * @param character The character
	 * @return whether the input was processed
	 */
	@Override
	public boolean keyTyped(char character) {
		return false;
	}

	/**
	 * Called when the screen was touched or a mouse button was pressed. The button parameter will be {@link Buttons#LEFT} on iOS.
	 *
	 * @param screenX The x coordinate, origin is in the upper left corner
	 * @param screenY The y coordinate, origin is in the upper left corner
	 * @param pointer the pointer for the event.
	 * @param button  the button
	 * @return whether the input was processed
	 */

	/**
	 * Called when the mouse was moved without any buttons being pressed. Will not be called on iOS.
	 *
	 * @param screenX
	 * @param screenY
	 * @return whether the input was processed
	 */
	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		return false;
	}

	/**
	 * Called when the mouse wheel was scrolled. Will not be called on iOS.
	 *
	 * @param amount the scroll amount, -1 or 1 depending on the direction the wheel was scrolled.
	 * @return whether the input was processed.
	 */
	@Override
	public boolean scrolled(int amount) {
		return false;
	}
}
