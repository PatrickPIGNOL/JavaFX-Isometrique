package game;

import java.util.List;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

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
				int vXTile = (int) ((vTile % vWidthInLine) * this.aTileWidth);
				int vYTile = (int) ((vTile / vWidthInLine) * this.aTileHeight);
				pGraphicsContext.drawImage
				(
					this.aTileSheet, 
					vXTile, 
					vYTile, 
					this.aTileWidth, 
					this.aTileHeight, 
					(vX * this.aTileWidth + this.aSpace) * (this.aScale), 
					(vCanvas.getHeight() / 4) + (vY * this.aTileHeight + this.aSpace) * (this.aScale), 
					this.aTileWidth * (this.aScale), 
					this.aTileHeight * (this.aScale)
				);
				
				int vXTile3D = (int) ((vTile % vWidthInLine3D) * this.aTileWidth3D);
				int vYTile3D = (int) (((vTile / vWidthInLine3D)) * this.aTileHeight3D);
				
				pGraphicsContext.drawImage
				(
					this.aTileSheet3D, 
					vXTile3D, 
					vYTile3D, 
					this.aTileWidth3D, 
					this.aTileHeight3D, 
					(vCanvas.getWidth() / 3) * 2 -(this.aTileWidth3D / 2) // x origin 
					+
					(vY * -(this.aTileWidth3D / 2) + vX * (this.aTileWidth3D / 2) + this.aSpace) * this.aScale, 
					(vCanvas.getHeight() / 4) // y origin 
					+ 
					(
						(vY * -2) + (vX * -2) // correction for picture extra pixels 
						+ 
						vX * (this.aTileHeight3D / 2) + vY * (this.aTileHeight3D / 2) + this.aSpace
					) * this.aScale, 
					this.aTileWidth3D * this.aScale, 
					this.aTileHeight3D * this.aScale
				);
				
				vX++;
			}
			vY++;
		}
	}
}
