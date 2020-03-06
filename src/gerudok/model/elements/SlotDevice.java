package gerudok.model.elements;

import gerudok.observer.MyObservable;
import gerudok.observer.MyObserver;
import gerudok.observer.ObserverEnum;

import java.awt.*;
import java.awt.geom.Point2D;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public abstract class SlotDevice extends SlotElement {

    protected Dimension size;//dimenzije elementa
    protected Point2D position;//pozicija elementa
    protected double angle;

    public SlotDevice(Point2D position, Dimension dimension, Stroke stroke, Paint paint, double angle){
        super(paint, stroke);
        this.size = dimension;
        this.position = position;
        this.angle = angle;
    }

    public SlotDevice(SlotDevice device){
        super(device);
        this.size = device.size;
        this.position = device.position;
        this.angle = device.angle;
    }

    public Point2D getPosition() {
        return position;
    }

    public void setPosition(Point2D position) {
        this.position = position;
    }

    public double getAngle() {
        return angle;
    }

    public void setAngle(double angle) {
        this.angle = angle;
    }

    public Dimension getSize() {
        return size;
    }

    public void setSize(Dimension size) {
        this.size = size;
    }
}
