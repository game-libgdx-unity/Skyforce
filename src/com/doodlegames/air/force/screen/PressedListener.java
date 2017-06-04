package com.doodlegames.air.force.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;

public class PressedListener extends InputListener {
	private float tapSquareSize = 14, touchDownX = -1, touchDownY = -1;
	private int pressedPointer;
	private int button;
	private boolean pressed, over;
 
	public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
		if (pressed) return false;
		if (pointer == 0 && button != this.button) return false;
		pressed = true;
		pressedPointer = pointer;
		touchDownX = x;
		touchDownY = y;
		return true;
	}
 
	public void touchDragged (InputEvent event, float x, float y, int pointer) {
		if (pointer != pressedPointer) return;
		pressed = isOver(event.getListenerActor(), x, y);
		if (pressed && pointer == 0 && !Gdx.input.isButtonPressed(button)) pressed = false;
		if (!pressed) {
			// Once outside the tap square, don't use the tap square anymore.
			touchDownX = -1;
			touchDownY = -1;
		}
	}
 
	public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
		if (pointer == pressedPointer) pressed = false;
	}
 
	public void enter (InputEvent event, float x, float y, int pointer, Actor fromActor) {
		if (pointer == -1) over = true;
	}
 
	public void exit (InputEvent event, float x, float y, int pointer, Actor toActor) {
		if (pointer == -1) over = false;
	}
 
	/** Returns true if the specified position is over the specified Actor or within the tap square. */
	public boolean isOver (Actor Actor, float x, float y) {
		Actor hit = Actor.hit(x, y, true);
		if (hit == null || !hit.isDescendantOf(Actor)) {
			if (touchDownX == -1 && touchDownY == -1) return false;
			return Math.abs(x - touchDownX) < tapSquareSize && Math.abs(y - touchDownY) < tapSquareSize;
		}
		return true;
	}
 
	public boolean isPressed () {
		return pressed;
	}
 
	public boolean isOver () {
		return over || pressed;
	}
 
	public void setTapSquareSize (float halfTapSquareSize) {
		tapSquareSize = halfTapSquareSize;
	}
 
	public float getTapSquareSize () {
		return tapSquareSize;
	}
 
	public float getTouchDownX () {
		return touchDownX;
	}
 
	public float getTouchDownY () {
		return touchDownY;
	}
 
	public int getButton () {
		return button;
	}
 
	/** Sets the button to listen for, all other buttons are ignored. Default is {@link Buttons#LEFT}. */
	public void setButton (int button) {
		this.button = button;
	}
}