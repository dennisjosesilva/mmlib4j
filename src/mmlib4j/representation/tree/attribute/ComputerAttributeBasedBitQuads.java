package mmlib4j.representation.tree.attribute;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import mmlib4j.images.GrayScaleImage;
import mmlib4j.images.impl.PixelIndexer;
import mmlib4j.representation.tree.NodeLevelSets;
import mmlib4j.representation.tree.componentTree.NodeCT;
import mmlib4j.utils.AdjacencyRelation;
import mmlib4j.utils.Utils;


/**
 * MMLib4J - Mathematical Morphology Library for Java 
 * @author Dennis Jose da Silva
 * @author Wonder Alexandre Luz Alves
 * 
 * Implementation of paper:
 * 
 */
public class ComputerAttributeBasedBitQuads extends AttributeComputedIncrementally {
	
	private AttributeBasedBitQuads attr[];
	private GrayScaleImage img; 
	private boolean isMaxtree;
	private AdjacencyRelation adj;
	
	private GrayScalePatternGroup Q1;
	private GrayScalePatternGroup Q1C4;
	private GrayScalePatternGroup Q2;
	private GrayScalePatternGroup QD;
	private GrayScalePatternGroup Q3;
	private GrayScalePatternGroup Q4;
	
	private GrayScalePatternGroup Q1T;
	private GrayScalePatternGroup Q1C4T;
	private GrayScalePatternGroup Q2T;
	private GrayScalePatternGroup QDT;
	private GrayScalePatternGroup Q3T;
	
	public ComputerAttributeBasedBitQuads(int numNode, NodeLevelSets root, GrayScaleImage img, AdjacencyRelation adj){
		long ti = System.currentTimeMillis();
		this.attr = new AttributeBasedBitQuads[numNode];
		this.img = img;
		this.adj = adj;
		this.isMaxtree = root.isNodeMaxtree();
		img.setPixelIndexer( PixelIndexer.getDefaultValueIndexer(img.getWidth(), img.getHeight()) );
		initializePatterns();
		computerAttribute(root);
		if(Utils.debug){
			long tf = System.currentTimeMillis();
			System.out.println("Tempo de execucao [extraction of attributes - bit-quads] "+ ((tf - ti) /1000.0)  + "s");
		}		
	}

	private void initializePatterns() {
		if (adj == AdjacencyRelation.getAdjacency4()) {
			createQ1C4Patterns();
			createQ1C4TPatterns();
		}
		else {
			createQ1Patterns();
			createQDPatterns();
			createQ1TPatterns();
			createQDTPattern();
		}
			
		createQ2Patterns();
		createQ3Patterns();
		createQ4Patterns();
		
		createQ2TPattern();		
		createQ3TPattern();
	}
	
	private void createQ1Patterns() {
		GrayScalePattern Q1P1 = new GrayScalePattern(3);
		GrayScalePattern Q1P2 = new GrayScalePattern(3);
		GrayScalePattern Q1P3 = new GrayScalePattern(3);
		GrayScalePattern Q1P4 = new GrayScalePattern(3);
		
		Q1P1.appendQuad(new LowerQuadBit(0, -1)).appendQuad(new LowerQuadBit(1, -1)).appendQuad(new LowerQuadBit(1, 0));
		Q1P2.appendQuad(new LowerQuadBit(-1, 0)).appendQuad(new LowerQuadBit(-1, -1)).appendQuad(new LowerQuadBit(0, -1));
		Q1P3.appendQuad(new LowerQuadBit(0, 1)).appendQuad(new LowerQuadBit(-1, 1)).appendQuad(new LowerQuadBit(-1, 0));
		Q1P4.appendQuad(new LowerQuadBit(1, 0)).appendQuad(new LowerQuadBit(1, 1)).appendQuad(new LowerQuadBit(0, 1));
		
		Q1 = new GrayScalePatternGroup(4);
		Q1.appendPattern(Q1P1).appendPattern(Q1P2).appendPattern(Q1P3).appendPattern(Q1P4);
	}
	
	private void createQ1C4Patterns() {
		GrayScalePattern Q1C4P1 = new GrayScalePattern(2);
		GrayScalePattern Q1C4P2 = new GrayScalePattern(2);
		GrayScalePattern Q1C4P3 = new GrayScalePattern(2);
		GrayScalePattern Q1C4P4 = new GrayScalePattern(2);
		
		Q1C4P1.appendQuad(new LowerQuadBit(0, -1)).appendQuad(new LowerQuadBit(1, 0));
		Q1C4P2.appendQuad(new LowerQuadBit(-1, 0)).appendQuad(new LowerQuadBit(0, -1));
		Q1C4P3.appendQuad(new LowerQuadBit(0, 1)).appendQuad(new LowerQuadBit(-1, 0));
		Q1C4P4.appendQuad(new LowerQuadBit(1, 0)).appendQuad(new LowerQuadBit(0, 1));
		
		Q1C4 = new GrayScalePatternGroup(4);
		Q1C4.appendPattern(Q1C4P1).appendPattern(Q1C4P2).appendPattern(Q1C4P3).appendPattern(Q1C4P4);
	}
	
	private void createQ2Patterns() {
		GrayScalePattern Q2P1 = new GrayScalePattern(3);
		GrayScalePattern Q2P2 = new GrayScalePattern(3);
		GrayScalePattern Q2P3 = new GrayScalePattern(3);
		GrayScalePattern Q2P4 = new GrayScalePattern(3);
		
		GrayScalePattern Q2P5 = new GrayScalePattern(3);
		GrayScalePattern Q2P6 = new GrayScalePattern(3);
		GrayScalePattern Q2P7 = new GrayScalePattern(3);
		GrayScalePattern Q2P8 = new GrayScalePattern(3);
		
		Q2P1.appendQuad(new GreaterOrEqualQuadBit(1, 0)).appendQuad(new LowerQuadBit(1, 1)).appendQuad(new LowerQuadBit(0, 1));
		Q2P2.appendQuad(new GreaterOrEqualQuadBit(0, 1)).appendQuad(new LowerQuadBit(-1, 1)).appendQuad(new LowerQuadBit(-1, 0));
		Q2P3.appendQuad(new GreaterOrEqualQuadBit(-1, 0)).appendQuad(new LowerQuadBit(-1, -1)).appendQuad(new LowerQuadBit(0, -1));
		Q2P4.appendQuad(new GreaterOrEqualQuadBit(0, -1)).appendQuad(new LowerQuadBit(1, -1)).appendQuad(new LowerQuadBit(1, 0));
		
		Q2P5.appendQuad(new LowerQuadBit(0, 1)).appendQuad(new LowerQuadBit(-1, 1)).appendQuad(new GreaterQuadBit(-1, 0));
		Q2P6.appendQuad(new LowerQuadBit(-1, 0)).appendQuad(new LowerQuadBit(-1, -1)).appendQuad(new GreaterQuadBit(0, -1));
		Q2P7.appendQuad(new LowerQuadBit(0, -1)).appendQuad(new LowerQuadBit(1, -1)).appendQuad(new GreaterQuadBit(1, 0));
		Q2P8.appendQuad(new LowerQuadBit(1, 0)).appendQuad(new LowerQuadBit(1, 1)).appendQuad(new GreaterQuadBit(0, 1));
		
		Q2 = new GrayScalePatternGroup(8);
		Q2.appendPattern(Q2P1).appendPattern(Q2P2).appendPattern(Q2P3).appendPattern(Q2P4).appendPattern(Q2P5).appendPattern(Q2P6)
		  .appendPattern(Q2P7).appendPattern(Q2P8);
	}
	
	private void createQDPatterns() {
		GrayScalePattern QDP1 = new GrayScalePattern(3);
		GrayScalePattern QDP2 = new GrayScalePattern(3);
		GrayScalePattern QDP3 = new GrayScalePattern(3);
		GrayScalePattern QDP4 = new GrayScalePattern(3);
		
		QDP1.appendQuad(new LowerQuadBit(1, 0)).appendQuad(new GreaterOrEqualQuadBit(1, 1)).appendQuad(new LowerQuadBit(0, 1));
		QDP2.appendQuad(new LowerQuadBit(0, -1)).appendQuad(new GreaterOrEqualQuadBit(1, -1)).appendQuad(new LowerQuadBit(1, 0));
		QDP3.appendQuad(new LowerQuadBit(-1, 0)).appendQuad(new GreaterQuadBit(-1, -1)).appendQuad(new LowerQuadBit(0, -1));
		QDP4.appendQuad(new LowerQuadBit(0, 1)).appendQuad(new GreaterQuadBit(-1, 1)).appendQuad(new LowerQuadBit(-1, 0));
		
		QD = new GrayScalePatternGroup(4);
		QD.appendPattern(QDP1).appendPattern(QDP2).appendPattern(QDP3).appendPattern(QDP4);		
	}
	
	private void createQ3Patterns() {
		GrayScalePattern Q3P1 = new GrayScalePattern(3);
		GrayScalePattern Q3P2 = new GrayScalePattern(3);
		GrayScalePattern Q3P3 = new GrayScalePattern(3);
		GrayScalePattern Q3P4 = new GrayScalePattern(3);
		
		GrayScalePattern Q3P5 = new GrayScalePattern(3);
		GrayScalePattern Q3P6 = new GrayScalePattern(3);
		GrayScalePattern Q3P7 = new GrayScalePattern(3);
		GrayScalePattern Q3P8 = new GrayScalePattern(3);
		
		GrayScalePattern Q3P9 = new GrayScalePattern(3);
		GrayScalePattern Q3P10 = new GrayScalePattern(3);
		GrayScalePattern Q3P11 = new GrayScalePattern(3);
		GrayScalePattern Q3P12 = new GrayScalePattern(3);
		
		Q3P1.appendQuad(new GreaterQuadBit(0, 1)).appendQuad(new LowerQuadBit(-1, 1)).appendQuad(new GreaterQuadBit(-1, 0));
		Q3P2.appendQuad(new GreaterQuadBit(1, 0)).appendQuad(new LowerQuadBit(1, 1)).appendQuad(new GreaterQuadBit(0, 1));
		Q3P3.appendQuad(new GreaterQuadBit(0, -1)).appendQuad(new LowerQuadBit(1, -1)).appendQuad(new GreaterQuadBit(1, 0));
		Q3P4.appendQuad(new GreaterQuadBit(-1, 0)).appendQuad(new LowerQuadBit(-1, -1)).appendQuad(new GreaterQuadBit(0, -1));
		
		Q3P5.appendQuad(new GreaterOrEqualQuadBit(1, 0)).appendQuad(new GreaterQuadBit(1, 1)).appendQuad(new LowerQuadBit(0, 1));
		Q3P6.appendQuad(new LowerQuadBit(0, 1)).appendQuad(new GreaterQuadBit(-1, 1)).appendQuad(new GreaterOrEqualQuadBit(-1, 0));
		Q3P7.appendQuad(new GreaterOrEqualQuadBit(-1, 0)).appendQuad(new GreaterQuadBit(-1, -1)).appendQuad(new LowerQuadBit(0, -1));
		Q3P8.appendQuad(new LowerQuadBit(0, -1)).appendQuad(new GreaterQuadBit(1, -1)).appendQuad(new GreaterOrEqualQuadBit(1, 0));
		
		Q3P9.appendQuad(new LowerQuadBit(-1, 0)).appendQuad(new GreaterOrEqualQuadBit(-1, -1)).appendQuad(new GreaterOrEqualQuadBit(0, -1));
		Q3P10.appendQuad(new GreaterOrEqualQuadBit(0, -1)).appendQuad(new GreaterOrEqualQuadBit(1, -1)).appendQuad(new LowerQuadBit(1, 0));
		Q3P11.appendQuad(new LowerQuadBit(1, 0)).appendQuad(new GreaterOrEqualQuadBit(1, 1)).appendQuad(new GreaterOrEqualQuadBit(0, 1));
		Q3P12.appendQuad(new GreaterOrEqualQuadBit(0, 1)).appendQuad(new GreaterOrEqualQuadBit(-1, 1)).appendQuad(new LowerQuadBit(-1, 0));
		
		Q3 = new GrayScalePatternGroup(12);
		Q3.appendPattern(Q3P1).appendPattern(Q3P2).appendPattern(Q3P3).appendPattern(Q3P4).appendPattern(Q3P5).appendPattern(Q3P6)
		  .appendPattern(Q3P7).appendPattern(Q3P8).appendPattern(Q3P9).appendPattern(Q3P10).appendPattern(Q3P11).appendPattern(Q3P12);
	}
	
	private void createQ4Patterns()	{
		GrayScalePattern Q4P1 = new GrayScalePattern(3);
		GrayScalePattern Q4P2 = new GrayScalePattern(3);
		GrayScalePattern Q4P3 = new GrayScalePattern(3);
		GrayScalePattern Q4P4 = new GrayScalePattern(3);
		
		Q4P1.appendQuad(new GreaterOrEqualQuadBit(1, 0)).appendQuad(new GreaterOrEqualQuadBit(1, 1)).appendQuad(new GreaterOrEqualQuadBit(0, 1));
		Q4P2.appendQuad(new GreaterOrEqualQuadBit(0, 1)).appendQuad(new GreaterOrEqualQuadBit(-1, 1)).appendQuad(new GreaterQuadBit(-1, 0));
		Q4P3.appendQuad(new GreaterOrEqualQuadBit(-1, 0)).appendQuad(new GreaterQuadBit(-1, -1)).appendQuad(new GreaterQuadBit(0, -1));
		Q4P4.appendQuad(new GreaterQuadBit(0, -1)).appendQuad(new GreaterQuadBit(1, -1)).appendQuad(new GreaterQuadBit(1, 0));
		
		Q4 = new GrayScalePatternGroup(4);
		Q4.appendPattern(Q4P1).appendPattern(Q4P2).appendPattern(Q4P3).appendPattern(Q4P4);
	}
	
	private void createQ1C4TPatterns() {
		GrayScalePattern Q1C4TP1 = new GrayScalePattern(2);
		GrayScalePattern Q1C4TP2 = new GrayScalePattern(2);
		GrayScalePattern Q1C4TP3 = new GrayScalePattern(2);
		GrayScalePattern Q1C4TP4 = new GrayScalePattern(2);
		GrayScalePattern Q1C4TP5 = new GrayScalePattern(2);
		GrayScalePattern Q1C4TP6 = new GrayScalePattern(2);
		GrayScalePattern Q1C4TP7 = new GrayScalePattern(2);
		GrayScalePattern Q1C4TP8 = new GrayScalePattern(2);
		
		Q1C4TP1.appendQuad(new LowerQuadBit(1, 1)).appendQuad(new GreaterQuadBit(0, 1));
		Q1C4TP2.appendQuad(new GreaterQuadBit(0, 1)).appendQuad(new GreaterQuadBit(-1, 1));
		Q1C4TP3.appendQuad(new LowerQuadBit(-1, -1)).appendQuad(new GreaterQuadBit(0, -1));
		Q1C4TP4.appendQuad(new GreaterQuadBit(0, -1)).appendQuad(new LowerQuadBit(1, -1));
		
		Q1C4TP5.appendQuad(new GreaterQuadBit(-1, 0)).appendQuad(new LowerOrEqualQuadBit(-1, -1));
		Q1C4TP6.appendQuad(new LowerOrEqualQuadBit(1, -1)).appendQuad(new GreaterQuadBit(1, 0));
		Q1C4TP7.appendQuad(new GreaterQuadBit(1, 0)).appendQuad(new LowerOrEqualQuadBit(1, 1));
		Q1C4TP8.appendQuad(new LowerOrEqualQuadBit(-1, 1)).appendQuad(new GreaterQuadBit(-1, 0));
		
		Q1C4T = new GrayScalePatternGroup(8);
		Q1C4T.appendPattern(Q1C4TP1).appendPattern(Q1C4TP2).appendPattern(Q1C4TP3).appendPattern(Q1C4TP4)
			 .appendPattern(Q1C4TP5).appendPattern(Q1C4TP6).appendPattern(Q1C4TP7).appendPattern(Q1C4TP8);
	}
	
	private void createQ1TPatterns(){
		GrayScalePattern Q1TP1 = new GrayScalePattern(3);
		GrayScalePattern Q1TP2 = new GrayScalePattern(3);
		GrayScalePattern Q1TP3 = new GrayScalePattern(3);
		GrayScalePattern Q1TP4 = new GrayScalePattern(3);
		
		GrayScalePattern Q1TP5 = new GrayScalePattern(3);
		GrayScalePattern Q1TP6 = new GrayScalePattern(3);
		GrayScalePattern Q1TP7 = new GrayScalePattern(3);
		GrayScalePattern Q1TP8 = new GrayScalePattern(3);
		
		GrayScalePattern Q1TP9 = new GrayScalePattern(3);
		GrayScalePattern Q1TP10 = new GrayScalePattern(3);
		GrayScalePattern Q1TP11 = new GrayScalePattern(3);
		GrayScalePattern Q1TP12 = new GrayScalePattern(3);
		
		Q1TP1.appendQuad(new LowerQuadBit(0, 1)).appendQuad(new GreaterQuadBit(-1, 1)).appendQuad(new LowerQuadBit(-1, 0));
		Q1TP2.appendQuad(new LowerQuadBit(1, 0)).appendQuad(new GreaterQuadBit(1, 1)).appendQuad(new LowerQuadBit(0, 1));
		Q1TP3.appendQuad(new LowerQuadBit(0, -1)).appendQuad(new GreaterQuadBit(1, -1)).appendQuad(new LowerQuadBit(1, 0));
		Q1TP4.appendQuad(new LowerQuadBit(-1, 0)).appendQuad(new GreaterQuadBit(-1, -1)).appendQuad(new LowerQuadBit(0, -1));
		
		Q1TP5.appendQuad(new LowerOrEqualQuadBit(1, 0)).appendQuad(new LowerQuadBit(1, 1)).appendQuad(new GreaterQuadBit(0, 1));
		Q1TP6.appendQuad(new GreaterQuadBit(0, 1)).appendQuad(new LowerQuadBit(-1, 1)).appendQuad(new LowerOrEqualQuadBit(-1, 0));
		Q1TP7.appendQuad(new LowerOrEqualQuadBit(-1, 0)).appendQuad(new LowerQuadBit(-1, -1)).appendQuad(new GreaterQuadBit(0, -1));
		Q1TP8.appendQuad(new GreaterQuadBit(0, -1)).appendQuad(new LowerQuadBit(1, -1)).appendQuad(new LowerOrEqualQuadBit(1, 0));
		
		Q1TP9.appendQuad(new GreaterQuadBit(-1, 0)).appendQuad(new LowerOrEqualQuadBit(-1,-1)).appendQuad(new LowerOrEqualQuadBit(0, -1));
		Q1TP10.appendQuad(new LowerOrEqualQuadBit(0, -1)).appendQuad(new LowerOrEqualQuadBit(1, -1)).appendQuad(new GreaterQuadBit(1, 0));
		Q1TP11.appendQuad(new GreaterQuadBit(1, 0)).appendQuad(new LowerOrEqualQuadBit(1, 1)).appendQuad(new LowerOrEqualQuadBit(0, 1));
		Q1TP12.appendQuad(new LowerOrEqualQuadBit(0, 1)).appendQuad(new LowerOrEqualQuadBit(-1, 1)).appendQuad(new GreaterQuadBit(-1, 0));
		
		Q1T = new GrayScalePatternGroup(12);
		Q1T.appendPattern(Q1TP1).appendPattern(Q1TP2).appendPattern(Q1TP3).appendPattern(Q1TP4).appendPattern(Q1TP5).appendPattern(Q1TP6)
		   .appendPattern(Q1TP7).appendPattern(Q1TP8).appendPattern(Q1TP9).appendPattern(Q1TP10).appendPattern(Q1TP11).appendPattern(Q1TP12);
	}
	
	private void createQ2TPattern()	{
		GrayScalePattern Q2TP1 = new GrayScalePattern(3);
		GrayScalePattern Q2TP2 = new GrayScalePattern(3);
		GrayScalePattern Q2TP3 = new GrayScalePattern(3);
		GrayScalePattern Q2TP4 = new GrayScalePattern(3);
		
		GrayScalePattern Q2TP5 = new GrayScalePattern(3);
		GrayScalePattern Q2TP6 = new GrayScalePattern(3);
		GrayScalePattern Q2TP7 = new GrayScalePattern(3);
		GrayScalePattern Q2TP8 = new GrayScalePattern(3);
		
		Q2TP1.appendQuad(new GreaterQuadBit(0, -1)).appendQuad(new GreaterQuadBit(1, -1)).appendQuad(new LowerQuadBit(1, 0));
		Q2TP2.appendQuad(new GreaterQuadBit(1, 0)).appendQuad(new GreaterQuadBit(1, 1)).appendQuad(new LowerQuadBit(0, 1));
		Q2TP3.appendQuad(new GreaterQuadBit(0, 1)).appendQuad(new GreaterQuadBit(-1, 1)).appendQuad(new LowerQuadBit(-1, 0));
		Q2TP4.appendQuad(new GreaterQuadBit(-1, 0)).appendQuad(new GreaterQuadBit(-1, -1)).appendQuad(new LowerQuadBit(0, -1));
		
		Q2TP5.appendQuad(new LowerOrEqualQuadBit(-1, 0)).appendQuad(new GreaterQuadBit(-1, -1)).appendQuad(new GreaterQuadBit(0, -1));
		Q2TP6.appendQuad(new LowerOrEqualQuadBit(0, -1)).appendQuad(new GreaterQuadBit(1, -1)).appendQuad(new GreaterQuadBit(1, 0));
		Q2TP7.appendQuad(new LowerOrEqualQuadBit(1, 0)).appendQuad(new GreaterQuadBit(1, 1)).appendQuad(new GreaterQuadBit(0, 1));
		Q2TP8.appendQuad(new LowerOrEqualQuadBit(0, 1)).appendQuad(new GreaterQuadBit(-1, 1)).appendQuad(new GreaterQuadBit(-1, 0));
		
		Q2T = new GrayScalePatternGroup(8);
		Q2T.appendPattern(Q2TP1).appendPattern(Q2TP2).appendPattern(Q2TP3).appendPattern(Q2TP4)
		   .appendPattern(Q2TP5).appendPattern(Q2TP6).appendPattern(Q2TP7).appendPattern(Q2TP8);
	}
	
	private void createQDTPattern() {
		GrayScalePattern QDTP1 = new GrayScalePattern(3);
		GrayScalePattern QDTP2 = new GrayScalePattern(3);
		GrayScalePattern QDTP3 = new GrayScalePattern(3);
		GrayScalePattern QDTP4 = new GrayScalePattern(3);
		
		QDTP1.appendQuad(new GreaterQuadBit(0, -1)).appendQuad(new LowerQuadBit(1, -1)).appendQuad(new GreaterQuadBit(1, 0));
		QDTP2.appendQuad(new GreaterQuadBit(1, 0)).appendQuad(new LowerQuadBit(1, 1)).appendQuad(new GreaterQuadBit(0, 1));
		QDTP3.appendQuad(new GreaterQuadBit(0, 1)).appendQuad(new LowerOrEqualQuadBit(-1, 1)).appendQuad(new GreaterQuadBit(-1, 0));
		QDTP4.appendQuad(new GreaterQuadBit(-1, 0)).appendQuad(new LowerOrEqualQuadBit(-1, -1)).appendQuad(new GreaterQuadBit(0, -1));
		
		QDT = new GrayScalePatternGroup(4);
		QDT.appendPattern(QDTP1).appendPattern(QDTP2).appendPattern(QDTP3).appendPattern(QDTP4);
	}
	
	private void createQ3TPattern() {
		GrayScalePattern Q3TP1 = new GrayScalePattern(3);
		GrayScalePattern Q3TP2 = new GrayScalePattern(3);
		GrayScalePattern Q3TP3 = new GrayScalePattern(3);
		GrayScalePattern Q3TP4 = new GrayScalePattern(3);
		
		Q3TP1.appendQuad(new GreaterQuadBit(0, -1)).appendQuad(new GreaterQuadBit(1, -1)).appendQuad(new GreaterQuadBit(1, 0));
		Q3TP2.appendQuad(new GreaterQuadBit(-1, 0)).appendQuad(new GreaterQuadBit(-1, -1)).appendQuad(new GreaterQuadBit(0, -1));
		Q3TP3.appendQuad(new GreaterQuadBit(0, 1)).appendQuad(new GreaterQuadBit(-1, 1)).appendQuad(new GreaterQuadBit(-1, 0));
		Q3TP4.appendQuad(new GreaterQuadBit(1, 0)).appendQuad(new GreaterQuadBit(1, 1)).appendQuad(new GreaterQuadBit(0, 1));
		
		Q3T = new GrayScalePatternGroup(4);
		Q3T.appendPattern(Q3TP1).appendPattern(Q3TP2).appendPattern(Q3TP3).appendPattern(Q3TP4);
	}

	
	private void computerLocalPattern(NodeLevelSets node, int p) {
		int px = p % img.getWidth();
		int py = p / img.getWidth();
		
		if (adj == AdjacencyRelation.getAdjacency4())
		{
			attr[node.getId()].countPatternC1C4 += Q1C4.count(px, py, img);
			attr[node.getId()].countPatternCT1C4 += Q1C4T.count(px, py, img);
		}
		else // 8-connectivity
		{
			attr[node.getId()].countPatternC1 += Q1.count(px, py, img);
			attr[node.getId()].countPatternCD += QD.count(px, py, img);
			attr[node.getId()].countPatternCTD += QDT.count(px, py, img);
			attr[node.getId()].countPatternCT1 += Q1T.count(px, py, img);
		}
		
		attr[node.getId()].countPatternC2 += Q2.count(px, py, img);		
		attr[node.getId()].countPatternC3 += Q3.count(px, py, img);		
		attr[node.getId()].countPatternC4 += Q4.count(px, py, img);
				
		attr[node.getId()].countPatternCT2 += Q2T.count(px, py, img);		
		attr[node.getId()].countPatternCT3 += Q3T.count(px, py, img);
	}
	
	
	private int getValue(int x, int y){
		if(isMaxtree)
			if(img.getIndex(x, y) == -1)
				return -1;
			else
				return img.getPixel(x, y);
		else
			if(img.getIndex(x, y) == -1)
				return 256;
			else
				return 255 - img.getPixel(x, y);
	}
	
	
	public void addAttributeInNodesCT(HashSet<NodeCT> list){
		for(NodeCT node: list){
			addAttributeInNodes(node);
		}
	}
	
	/*
	TODO: 
	public void addAttributeInNodesToS(HashSet<NodeToS> hashSet){
		for(NodeLevelSets node: hashSet){
			addAttributeInNodes(node);
		}
	} 
	*/
	
	public void addAttributeInNodes(NodeLevelSets node){
		node.addAttribute(Attribute.BIT_QUADS_PERIMETER, new Attribute(Attribute.BIT_QUADS_PERIMETER, attr[ node.getId() ].getPerimeter()));
		node.addAttribute(Attribute.BIT_QUADS_EULER_NUMBER, new Attribute(Attribute.BIT_QUADS_EULER_NUMBER, attr[ node.getId() ].getNumberEuler()));
		node.addAttribute(Attribute.BIT_QUADS_HOLE_NUMBER, new Attribute(Attribute.BIT_QUADS_HOLE_NUMBER, attr[ node.getId() ].getNumberHoles()));
		node.addAttribute(Attribute.BIT_QUADS_PERIMETER_CONTINUOUS, new Attribute(Attribute.BIT_QUADS_PERIMETER_CONTINUOUS, attr[ node.getId() ].getPerimeterContinuous()));
		node.addAttribute(Attribute.BIT_QUADS_CIRCULARITY, new Attribute(Attribute.BIT_QUADS_CIRCULARITY, attr[ node.getId() ].getCircularity(node.getArea())));
		node.addAttribute(Attribute.BIT_QUADS_AVERAGE_AREA, new Attribute(Attribute.BIT_QUADS_AVERAGE_AREA, attr[ node.getId() ].getAreaAverage(node.getArea())));
		node.addAttribute(Attribute.BIT_QUADS_AVERAGE_PERIMETER, new Attribute(Attribute.BIT_QUADS_AVERAGE_PERIMETER, attr[ node.getId() ].getPerimeterAverage()));
		node.addAttribute(Attribute.BIT_QUADS_AVERAGE_LENGTH, new Attribute(Attribute.BIT_QUADS_AVERAGE_LENGTH, attr[ node.getId() ].getLengthAverage()));
		node.addAttribute(Attribute.BIT_QUADS_AVERAGE_WIDTH, new Attribute(Attribute.BIT_QUADS_AVERAGE_WIDTH, attr[ node.getId() ].getWidthAverage(node.getArea())));
		node.addAttribute(Attribute.BIT_QUADS_AREA, new Attribute(Attribute.BIT_QUADS_AREA, attr[node.getId()].getArea()));
		node.addAttribute(Attribute.BIT_QUADS_AREA_DUDA, new Attribute(Attribute.BIT_QUADS_AREA_DUDA, attr[node.getId()].getAreaDuda()));
	}
	
	
	public void preProcessing(NodeLevelSets node) {
		attr[node.getId()] = new AttributeBasedBitQuads();
		for(int p: node.getCanonicalPixels()){
			computerLocalPattern(node, p);
		}		
		
		//System.out.println(attr[node.getId()].printPattern());
	}
	
	public void mergeChildren(NodeLevelSets node, NodeLevelSets son) {
		if (adj == AdjacencyRelation.getAdjacency4())
			attr[node.getId()].countPatternChildrenC1C4 += attr[son.getId()].countPatternC1C4;
		else {
			attr[node.getId()].countPatternChildrenC1 += attr[son.getId()].countPatternC1;
			attr[node.getId()].countPatternChildrenCD += attr[son.getId()].countPatternCD;
		}

		attr[node.getId()].countPatternChildrenC2 += attr[son.getId()].countPatternC2;
		attr[node.getId()].countPatternChildrenC3 += attr[son.getId()].countPatternC3;
		attr[node.getId()].countPatternChildrenC4 += attr[son.getId()].countPatternC4;
	}

	public void posProcessing(NodeLevelSets root) {
		//pos-processing root
		if (adj == AdjacencyRelation.getAdjacency4()) 
			attr[root.getId()].countPatternC1C4 = attr[root.getId()].countPatternC1C4 + attr[root.getId()].countPatternChildrenC1C4 - attr[root.getId()].countPatternCT1C4;
		else {
			attr[root.getId()].countPatternC1 = attr[root.getId()].countPatternC1 + attr[root.getId()].countPatternChildrenC1 - attr[root.getId()].countPatternCT1;
			attr[root.getId()].countPatternCD = attr[root.getId()].countPatternCD + attr[root.getId()].countPatternChildrenCD - attr[root.getId()].countPatternCTD;
		}
		
		attr[root.getId()].countPatternC2 = attr[root.getId()].countPatternC2 + attr[root.getId()].countPatternChildrenC2 - attr[root.getId()].countPatternCT2;
		attr[root.getId()].countPatternC3 = attr[root.getId()].countPatternC3 + attr[root.getId()].countPatternChildrenC3 - attr[root.getId()].countPatternCT3;
		attr[root.getId()].countPatternC4 = attr[root.getId()].countPatternC4 + attr[root.getId()].countPatternChildrenC4;
		
	}
	
	
	
	
	//////////////////// private classes ///////////////
	
	private abstract class GrayScaleQuadBit	{
		public int xPosition;
		public int yPosition;
		
		public GrayScaleQuadBit(int xPosition, int yPosition){
			this.xPosition = xPosition;
			this.yPosition = yPosition;
		}
		
		public abstract boolean compare(int px, int py, GrayScaleImage img);
	}
	
	private class GreaterOrEqualQuadBit extends GrayScaleQuadBit {
		
		public GreaterOrEqualQuadBit(int xPosition, int yPosition) {
			super(xPosition, yPosition);			
		}

		public boolean compare(int px, int py, GrayScaleImage img) { 
			return getValue(px, py) <= getValue(px + xPosition, py + yPosition); 
		} 
	}
	
	private class GreaterQuadBit extends GrayScaleQuadBit {
		public GreaterQuadBit(int xPosition, int yPosition) {
			super(xPosition, yPosition);
		}
		
		public boolean compare(int px, int py, GrayScaleImage img) {
			return getValue(px, py) < getValue(px + xPosition, py + yPosition);
		}
	}
	
	private class LowerOrEqualQuadBit extends GrayScaleQuadBit {
		public LowerOrEqualQuadBit(int xPosition, int yPosition) {
			super(xPosition, yPosition);
		}
		
		public boolean compare(int px, int py, GrayScaleImage img) {
			return getValue(px, py) >= getValue(px + xPosition, py + yPosition); 
		}
	}
	
	private class LowerQuadBit extends GrayScaleQuadBit {
		public LowerQuadBit(int xPosition, int yPosition) {
			super(xPosition, yPosition);
		}
		
		public boolean compare(int px, int py, GrayScaleImage img) {
			return getValue(px, py) > getValue(px + xPosition, py + yPosition);
		}
	}
	
	private class GrayScalePattern {
		//private List<GrayScaleQuadBit> quads;
		private GrayScaleQuadBit quads[];
		private int count = 0;
		public GrayScalePattern(int size){
			//quads = new ArrayList<ComputerAttributeBasedBitQuads.GrayScaleQuadBit>();
			quads = new GrayScaleQuadBit[size];
		}
		
		public GrayScalePattern appendQuad(GrayScaleQuadBit quad) {
			//quads.add(quad);
			quads[count++] = quad;
			return this;
		}
		
		public boolean match(int px, int py, GrayScaleImage img) {
			for (GrayScaleQuadBit q : quads) {
				if (!q.compare(px, py, img))
					return false;
			}
			return true;
		}
	}
	
	private class GrayScalePatternGroup	{
		//private List<GrayScalePattern> patterns;
		private GrayScalePattern patterns[];
		private int count=0;
		
		public GrayScalePatternGroup(int size) {
			//patterns = new ArrayList<ComputerAttributeBasedBitQuads.GrayScalePattern>();
			patterns = new GrayScalePattern[size];
		}
		
		public GrayScalePatternGroup appendPattern(GrayScalePattern pattern) {
			//patterns.add(pattern);
			patterns[count++] = pattern;
			return this;
		}
		
		public int count(int px, int py, GrayScaleImage img) {
			int c = 0;
			for (GrayScalePattern pattern: patterns) {
				if (pattern.match(px, py, img))
					c++;
			}			
			return c;
		}
	}
	
	 private class AttributeBasedBitQuads {
	
		 int countPatternCD = 0;
		 int countPatternC4 = 0;
		 int countPatternC3 = 0;
		 int countPatternC2 = 0;
		 int countPatternC1C4 = 0;
		 int countPatternC1 = 0;
		
		 int countPatternCTD = 0;
		 int countPatternCT3 = 0;
		 int countPatternCT2 = 0;
		 int countPatternCT1C4 = 0;
		 int countPatternCT1 = 0;
		
		 int countPatternChildrenC1 = 0;
		 int countPatternChildrenC1C4 = 0;
		 int countPatternChildrenC2 = 0;
		 int countPatternChildrenCD = 0;
		 int countPatternChildrenC3 = 0;
		 int countPatternChildrenC4 = 0;
		
		 public int getNumberEuler(){
			
			 //System.out.println("Q1  ---> " + countPatternC1);
			 //System.out.println("Q3  ---> " + countPatternC3);
			 //System.out.println("QD  ---> " + countPatternCD);
			 //System.out.println("Euler -> " + ((countPatternC1 - countPatternC3 - (2 * countPatternCD)) / 4));
			
			 if(adj == AdjacencyRelation.getAdjacency4())
				 return (countPatternC1C4 - countPatternC3) / 4;
			 else
				 return (countPatternC1 - countPatternC3 - (2 * countPatternCD)) / 4;
		 }
		
		 public int getNumberHoles() {
			 return 1 - getNumberEuler();
		 }
		
		 public int getPerimeter() {
			 return countPatternC1 + countPatternC2 + countPatternC3 + (2*countPatternCD);
		 }
		
		 public int getArea(){
			 return (countPatternC1 + 2*countPatternC2 + 3*countPatternC3 + 4*countPatternC4 + 2*countPatternCD) / 4;
		 }
		
		 //area de objetos continuos => Duda	
		 public double getAreaDuda(){
			 return (1.0/4.0*countPatternC1 + 1.0/2.0*countPatternC2 + 7.0/8.0*countPatternC3 + countPatternC4 + 3.0/4.0*countPatternCD);
		 }
		
		
		 //perimetro de objetos continuos => Duda
		 public double getPerimeterContinuous(){
			 //return countPatternC2 + ( (1.0/Math.sqrt(2)) * (countPatternC1 + countPatternC3 + 2*countPatternCD) );
			 return countPatternC2 + ((countPatternC1 + countPatternC3)/ 1.5);//Math.sqrt(2.0));
		 }
		
		 public double getCircularity(int area){
			 return (4.0 * Math.PI * area) / Math.pow(getPerimeterContinuous(), 2);
		 }
		
		
		 public double getAreaAverage(int area){
			 return (area  / (double) getNumberEuler());
		 }
		 
		 public double getPerimeterAverage(){
			 return (getPerimeterContinuous()  / (double) getNumberEuler());
		 }
		
		 public double getLengthAverage(){
			 return (getPerimeterAverage()  / 2.0);
		 }
		
		 public double getWidthAverage(int area){
			 return (2.0 * getAreaAverage(area)  / getPerimeterAverage());
		 }
		
		public String printPattern() {
			String s = "Q1: " + countPatternC1;
			s += "\tQ1C4: " + countPatternC1C4;
			s += "\tQ2: " + countPatternC2;
			s += "\tQ3: " + countPatternC3;
			s += "\tQD: " + countPatternCD;
			s += "\tQ4: " + countPatternC4;
			s += "\tQT1C4: " + countPatternCT1C4;
			s += "\tQT1: " + countPatternCT1;
			s += "\tQT2: " + countPatternCT2;
			s += "\tQT3: " + countPatternCT3;
			s += "\tQTD: " + countPatternCTD;
			return s;
		}	
	}
}