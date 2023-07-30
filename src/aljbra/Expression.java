package aljbra;

import be.ugent.caagt.jmathtex.TeXFormula;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;

public abstract class Expression implements Comparable<Expression>{
    private static int numOpened = 0;
    private final static int defaultWidth = GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds().width / 4;
    private final static int defaultHeight = GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds().height / 5;

    public final Expression add(Expression e){
        if (e.equals(Scalar.ZERO)){
            return this;
        } else if (this.isAdditionCompatible(e)){
            return this.__add__(e);
        } else if (e.isAdditionCompatible(this)){
            return e.add(this);
        } else {
            return new Sum(this,e);
        }
    }
    public final Expression subtract(Expression e){
        return this.add(e.negate());
    }
    public final Expression multiply(Expression e){
        if (e.equals(Scalar.ZERO)){
            return Scalar.ZERO;
        } else if (e.equals(Scalar.ONE)){
            return this;
        } else if (this.isMultiplicationCompatible(e)){
            return this.__multiply__(e);
        } else if (e.isMultiplicationCompatible(this)){
            return e.multiply(this);
        } else {
            return new Product(this,e);
        }
    }
    public final Expression divide(Expression e){
        return this.multiply(e.invert());
    }
    public final Expression pow(Expression e){
        if (e.equals(Scalar.ONE) || this.equals(Scalar.ZERO) || this.equals(Scalar.ONE)){
            return this;
        } else if (e.equals(Scalar.ZERO)){
            return Scalar.ONE;
        } else if (this.isPowCompatible(e)){
            return this.__pow__(e);
        } else {
            return new Exponential(this,e);
        }
    }
    public abstract Expression negate();
    public abstract Expression invert();
    public final Expression sqrt(){
        return this.nRoot(Scalar.TWO);
    }
    public final Expression nRoot(Expression e){
        return this.pow(e.invert());
    }
    public abstract boolean equals(Expression e);
    public abstract double eval(HashMap<String,Double> values);
    public abstract boolean contains(Expression e);
    public abstract Expression replace(Expression e, Expression with);
    public abstract String toString();
    public abstract String toLaTeX();
    public abstract Expression simplify();
    public final Expression fullSimplify(){
        Expression simplified = this.simplify();
        Expression prev = this;
        while (!prev.equals(simplified)){
            prev = simplified;
            simplified = simplified.simplify();
        }
        return simplified;
    }
    public final void visualize(){
        visualize("");
    }
    public final void visualize(String equationLabel){
        TeXFormula formula = new TeXFormula(toLaTeX());
        Icon icon = formula.createTeXIcon(0, 25.0F);
        JLabel label = new JLabel(icon);
        label.setForeground(Color.BLACK);
        JPanel panel = new JPanel();
        panel.add(label);
        JFrame window = new JFrame(equationLabel);
        window.setLocation((numOpened % 4) * defaultWidth,(numOpened / 5) * defaultHeight);
        numOpened++;
        window.setContentPane(panel);
        window.setDefaultCloseOperation(3);
        window.pack();
        window.setVisible(true);
    }
    public final void save(String fileName){
        TeXFormula formula = new TeXFormula(toLaTeX());
        Icon icon = formula.createTeXIcon(0, 25.0F);
        JLabel label = new JLabel(icon);
        label.setForeground(Color.BLACK);
        BufferedImage image = new BufferedImage(icon.getIconWidth() + 20,icon.getIconHeight() + 20,1);
        Graphics2D g = image.createGraphics();
        g.setColor(Color.WHITE);
        g.fillRect(0,0,image.getWidth(),image.getHeight());
        icon.paintIcon(label,g,10,10);
        try {
            ImageIO.write(image,"png",new File(fileName));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    abstract Expression __add__(Expression e);
    abstract Expression __multiply__(Expression e);
    abstract Expression __pow__(Expression e);
    abstract boolean isAdditionCompatible(Expression e);
    abstract boolean isMultiplicationCompatible(Expression e);
    abstract boolean isPowCompatible(Expression e);

    final static ArrayList<long[]> clone(ArrayList<long[]> arrayList){
        ArrayList<long[]> clone = new ArrayList<>();
        for (int i = 0; i < arrayList.size();i++){
            clone.add(arrayList.get(i).clone());
        }
        return clone;
    }

    @Override
    public int compareTo(Expression o) {
        int index1 = this.getSimplificationIndex();
        int index2 = o.getSimplificationIndex();
        if (index1 == index2){
            return this.toString().compareTo(o.toString());
        } else {
            return Integer.compare(index1,index2);
        }
    }

    private final int getSimplificationIndex(){
        if (this instanceof Exponential && ((Exponential) this).exponent instanceof Fraction){
            return 4;
        }
        try {
            eval(null);
        } catch (NullPointerException n){
            return 2;
        }
        if (this instanceof Fraction || this instanceof Scalar){
            return 0;
        } else {
            return 1;
        }
    }
}
