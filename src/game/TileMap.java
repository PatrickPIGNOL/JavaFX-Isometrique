package game;

import java.util.List;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class TileMap 
{
	private List<List<Integer>> aTileMap;
	private Image aTileSheet;
	private Image aTileSheet3D;
	private double aTileWidth;
	private double aTileHeight;
	private double aTileWidth3D;
	private double aTileHeight3D;
	private double aSpace;
	private double aScale;
	private double aXOrigin;
	private double aYOrigin;
	TileMap(Image pTileSheet, Image pTileSheet3D, double pTileWidth, double pTileHeight, double pTileWidth3D, double pTileHeight3D, List<List<Integer>> pTableau, double pSpace, double pScale)
	{
		this.aTileMap = pTableau;
		this.aTileSheet = pTileSheet;
		this.aTileSheet3D = pTileSheet3D;
		this.aTileWidth = pTileWidth;
		this.aTileHeight = pTileHeight;
		this.aTileWidth3D = pTileWidth3D;
		this.aTileHeight3D = pTileHeight3D;
		this.aSpace = pSpace;
		this.aScale = pScale;
		this.aXOrigin = 0.0;
		this.aYOrigin = 0.0;
	}
	
	public void mChangeMap(List<List<Integer>> pTableau)
	{
		for(List<Integer> vLine : this.aTileMap)
		{
			vLine.clear();
		}
		this.aTileMap.clear();
		this.aTileMap = null;
		this.aTileMap = pTableau;
	}
	
	public void mUpdate(double pDeltaTime)
	{
		this.aScale = 1.0;
	}
	
	public void mDraw(GraphicsContext pGraphicsContext)	
	{
		Canvas vCanvas = pGraphicsContext.getCanvas();
		int vWidthInLine = (int) (this.aTileSheet.getWidth() / this.aTileWidth);
		int vWidthInLine3D = (int) (this.aTileSheet3D.getWidth() / this.aTileWidth3D);
		int vY = 0;
		for(List<Integer> vLine : this.aTileMap)
		{
			int vX = 0;
			for(Integer vTile : vLine)
			{
				int vXTile3D = (int) ((vTile % vWidthInLine3D) * this.aTileWidth3D);
				int vYTile3D = (int) (((vTile / vWidthInLine3D)) * this.aTileHeight3D);
				double vX3D = (vCanvas.getWidth() / 3) * 2 + this.aXOrigin * this.aTileWidth -(this.aTileWidth3D / 2) + (vY * -(this.aTileWidth3D / 2) + vX * (this.aTileWidth3D / 2) + this.aSpace) * this.aScale;
				double vY3D = (vCanvas.getHeight() / 4) + this.aYOrigin * this.aTileHeight + ((vY * -2) + (vX * -2) + vX * (this.aTileHeight3D / 2) + vY * (this.aTileHeight3D / 2) + this.aSpace) * this.aScale;
				if
				(
					(
						(vX3D >= -60) 
						&& 
						(vX3D < vCanvas.getWidth())
					)
					&&
					(
						(vY3D >= -60) 
						&& 
						(vY3D < vCanvas.getHeight())
					)
				)
				{
					pGraphicsContext.drawImage
					(
						this.aTileSheet3D, 
						vXTile3D, 
						vYTile3D, 
						this.aTileWidth3D, 
						this.aTileHeight3D, 
						vX3D, 
						vY3D, 
						this.aTileWidth3D * this.aScale, 
						this.aTileHeight3D * this.aScale
					);
				}
				vX++;
			}
			vY++;
		}
		vY=0;
		for(List<Integer> vLine : this.aTileMap)
		{
			int vX = 0;
			for(Integer vTile : vLine)
			{
				int vXTile = (int) ((vTile % vWidthInLine) * this.aTileWidth);
				int vYTile = (int) ((vTile / vWidthInLine) * this.aTileHeight);
				double vX2D = (vX * this.aTileWidth + this.aSpace) * (this.aScale * 0.01);
				double vY2D = (vY * this.aTileHeight + this.aSpace) * (this.aScale * 0.01);
				if
				(
					(
						(vX2D >= 0) 
						&& 
						(vX2D < vCanvas.getWidth())
					)
					&&
					(
						(vY2D >= 0) 
						&& 
						(vY2D < vCanvas.getHeight())
					)
				)
				{
					pGraphicsContext.drawImage
					(
						this.aTileSheet, 
						vXTile, 
						vYTile, 
						this.aTileWidth, 
						this.aTileHeight, 
						vX2D, 
						vY2D, 
						this.aTileWidth * (this.aScale * 0.1), 
						this.aTileHeight * (this.aScale * 0.1)
					);
				}				
				vX++;
			}
			vY++;
		}
	}

	public void mKeyPress(KeyEvent e) 
	{
		switch(e.getCode())
		{
			case LEFT:
			{
				this.aXOrigin += 1.0;
			}break;
			case RIGHT:
			{
				this.aXOrigin -= 1.0;
			}break;
			case UP:
			{
				this.aYOrigin += 1.0;
			}break;
			case DOWN:
			{
				this.aYOrigin -= 1.0;
			}break;
		}
		
	}
}
