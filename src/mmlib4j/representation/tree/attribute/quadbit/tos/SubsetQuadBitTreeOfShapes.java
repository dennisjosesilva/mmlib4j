package mmlib4j.representation.tree.attribute.quadbit.tos;

import mmlib4j.images.GrayScaleImage;
import mmlib4j.representation.tree.attribute.quadbit.SubsetQuadBit;
import mmlib4j.representation.tree.tos.BuilderTreeOfShape;
import mmlib4j.representation.tree.tos.ConnectedFilteringByTreeOfShape;
import mmlib4j.representation.tree.tos.TreeOfShape;
import mmlib4j.utils.AdjacencyRelation;

public class SubsetQuadBitTreeOfShapes extends SubsetQuadBit{

	private BuilderTreeOfShape tosBuilder;
	private TreeOfShape tos;
	private GrayScaleImage image;
	private short[] imageU;
	private AdjacencyRelation adj4;
	
	public SubsetQuadBitTreeOfShapes(ConnectedFilteringByTreeOfShape tos, int px, int py) {
		super(px, py);
		this.tos = tos;
		this.tosBuilder = tos.getBuilder();
		this.image = tos.getInputImage();
		this.imageU = this.tosBuilder.getImageU();
		this.adj4 = AdjacencyRelation.getAdjacency8();
	}
	
	@Override
	public boolean match(int px, int py) {
		int upx = 2*px+1;
		int upy = 2*py+1;
		
		int Uwidth = (2 * image.getWidth()) + 1;
		//int Uheight = (2* image.getHeight()) + 1;
		
		int qx = px + dx;
		int qy = py + dy;
		
		int uqx = qx * 2 + 1;
		int uqy = qy * 2 + 1;
		
		if (!image.isPixelValid(qx, qy)) 
			return false; 
		
		int[] dx = adj4.getVectorX();
		int[] dy = adj4.getVectorY();
			
		for (int i = 0; i < dx.length; i++) {
			int ux = uqx + dx[i];
			int uy = uqy + dy[i];
			
			// There is ancestor relationship between SC(p,T) and SC(q,T)
			if (imageU[uy * Uwidth + ux] == image.getValue(px, py)) {
					return true;
			}
		}
			
		return false;
	}	
}