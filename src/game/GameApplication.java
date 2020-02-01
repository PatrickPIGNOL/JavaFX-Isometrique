package game;



import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class GameApplication extends Application
{
	private Stage aStage;
	private Scene aScene;
	private Group aGroup;
	private Canvas aCanvas;
	private GraphicsContext aGraphicsContext;
	private double aFPS;
	private double aFPSTime;
	private double aFrameCount;
	private double aNanoTime;
	private double aTime;
	
	private TileMap aTileMap;
	private Random aRandom;
	
	@Override
	public void start(Stage pStage) throws Exception 
	{
		this.aStage = pStage;    
		this.aGroup = new Group();
	    this.aScene = new Scene(this.aGroup);
	    this.aStage.setScene(this.aScene);
	    this.aCanvas = new Canvas( 1024, 768 );
	    this.aGroup.getChildren().add(this.aCanvas);
	    this.aGraphicsContext = this.aCanvas.getGraphicsContext2D();
	    this.aNanoTime = System.nanoTime();
	    this.aFrameCount = this.aNanoTime;
	    this.aScene.setOnKeyPressed
	    (
	    	new EventHandler<KeyEvent>()
	        {
	    		public void handle(KeyEvent e)
	            {
	                 mOnKeyPressed(e);
	            }
	        }
	    );
	    this.aScene.setOnKeyReleased
	    (
	    	new EventHandler<KeyEvent>()
	    	{
	    		public void handle(KeyEvent e)
	    		{
	    			mOnKeyReleased(e);
	    		}
	    	}
	    );
	    this.aScene.setOnMouseMoved
	    (
	    	new EventHandler<MouseEvent>()
	    	{
	    		public void handle(MouseEvent e)
	    		{
	    			mOnMouseMoved(e);
	            }
	    	}
	    );
	    this.aScene.setOnMouseClicked
	    (
	    	new EventHandler<MouseEvent>()
	    	{
	    		public void handle(MouseEvent e)
	    		{
	    			mOnMouseClicked(e);
	            }
	    	}
	    );

	    this.aStage.setResizable(false);
	    this.mLoad();	    
	    this.mStart();
	    new AnimationTimer()
	    {
	    	public void handle(long pCurrentNanoTime)
	    	{
	    		double vCurrentNanoTime = Double.valueOf(pCurrentNanoTime);
	    		mLoop(vCurrentNanoTime);
	    	}
	    }.start();	    
		this.aStage.show();
	}
	
	private void mLoop(double pCurrentNanoTime)
	{		
		double vDeltaTime = pCurrentNanoTime - this.aNanoTime;
		this.aTime += vDeltaTime;
		this.aFPSTime += vDeltaTime;
		
		double vNanoTimePerSeconds = 1000000000.0;
		double vFPS = 120;
		double vNanoTimePerFPS = vNanoTimePerSeconds / vFPS;
		this.mUpdate(vDeltaTime/vNanoTimePerSeconds);
        // limit acceleration card overheat and CPU usage ...
		if(this.aFPSTime > vNanoTimePerFPS)
        {
			this.aFrameCount++;
	    	this.aGraphicsContext.setFill(Color.BLACK);
	    	this.aGraphicsContext.fillRect(0.0, 0.0, this.aCanvas.getWidth(), this.aCanvas.getHeight());
	    	this.mDraw(this.aGraphicsContext);
			//this.mDrawFPS(vDeltaTime);
			this.aFPSTime = 0.0;
		}	
		
        if(this.aTime > vNanoTimePerSeconds)
		{			
        	this.aFPS = this.aFrameCount;
			this.aFrameCount = 0.0;
			this.aTime = 0.0;
		}
		
		this.aNanoTime = pCurrentNanoTime;
	}
	
	public void mLoad()
	{
		this.aRandom = new Random(System.nanoTime());
	    this.aStage.setTitle("JavaFX : Isométrique");
		Image vTileSheet = new Image("2DTiles.png");
		Image vTileSheet3D = new Image("3DTiles.png");
		List<List<Integer>> vTable = this.mCreateMap();
		this.aTileMap = new TileMap(vTileSheet, vTileSheet3D, 32, 32, 64, 36, vTable, 10, 1);
	}
	
	private List<List<Integer>> mCreateMap()
	{		
		List<List<Integer>> vTable = new ArrayList<List<Integer>>();
		for(int vY = 0; vY < 10; vY++)
		{
			List<Integer> vLine = new ArrayList<Integer>();
			for(int vX = 0; vX < 10; vX++)
			{
				int vValue = this.aRandom.nextInt(2);
				vLine.add(vValue);
			}
			vTable.add(vLine);
		}
		return vTable;
	}
	
	private void mNewMap()
	{
		this.aTileMap.mChangeMap(this.mCreateMap());
	}
	
	public void mStart()
	{
	}
	
	public void mUpdate(double pDeltaTime)
    {				
		this.mOnUpdate(pDeltaTime);        
    }
	
	private void mOnUpdate(double pDeltaTime)
	{
		this.aTileMap.mUpdate(pDeltaTime);
	}
	
	public void mDraw(GraphicsContext pGraphicsContext)
	{
		this.mOnDraw(pGraphicsContext);
	}
	
	private void mOnDraw(GraphicsContext pGraphicsContext)
	{
		this.aTileMap.mDraw(pGraphicsContext);
	}
	
	private void mOnKeyPressed(KeyEvent e)
	{
		if(e.getCode() == KeyCode.SPACE)
		{
			this.mNewMap();
		}
	}
	
	private void mOnKeyReleased(KeyEvent e)
	{
		
	}
	
	private void mOnMouseMoved(MouseEvent e)
	{
		
	}
	
	private void mOnMouseClicked(MouseEvent e)
	{
		
	}
	
	private void mDrawFPS(double pDeltaTime)
	{
		double vNanoTimePerSeconds = 1000000000.0;
		double vFPS = 240;
		double vNanoTimePerFPS = vNanoTimePerSeconds / vFPS;
		
		Font vFont = Font.font( "Times New Roman", FontWeight.BOLD, 14 );
		this.mDrawText(10.0, 20.0, vFont, "Delta: " + pDeltaTime, 0.0, Color.GREEN, Color.BLACK);
		this.mDrawText(10.0, 40.0, vFont, "FPS: " + this.aFPS, 0.0, Color.GREEN, Color.BLACK);
		this.mDrawText(10.0, 60.0, vFont, "FPStime: " + this.aFPSTime / vNanoTimePerSeconds, 0.0, Color.GREEN, Color.BLACK);
		this.mDrawText(10.0, 80.0, vFont, "n/FPS: " + vNanoTimePerFPS / vNanoTimePerSeconds, 0.0, Color.GREEN, Color.BLACK);
	}
	
	private void mDrawText(double pX, double pY, Font pFont, String pText, double pLineWidth, Paint pFillColor, Paint pStrokeColor)
	{
		this.aGraphicsContext.setFill(pFillColor);
		this.aGraphicsContext.setFont(pFont);
	    this.aGraphicsContext.fillText(pText, pX, pY);
		if(pLineWidth > 0.0)
		{
			this.aGraphicsContext.setStroke(pStrokeColor);
			this.aGraphicsContext.setLineWidth(pLineWidth);
		    this.aGraphicsContext.strokeText(pText, pX, pY);
		}
	}
}
