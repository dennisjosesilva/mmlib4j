package mmlib4j.representation.tree.attribute.quadbit.maxtree;

import mmlib4j.images.GrayScaleImage;
import mmlib4j.representation.tree.attribute.quadbit.ProperSubsetQuadBit;

public class ProperSubsetQuadBitMaxtree extends ProperSubsetQuadBit{
	
	private GrayScaleImage img;
	
	public ProperSubsetQuadBitMaxtree(GrayScaleImage img, int dx, int dy) {
		super(dx, dy);
		this.img = img;
	}
	
	@Override
	public boolean match(int px, int py) {
		return getPixelValue(px, py) < getPixelValue(px + dx, py + dy);
	}

	private int getPixelValue(int px, int py) {
		int index = img.getIndex(px, py);
		if (index < 0)
			return -1;
		return img.getValue(index);
	}
}
