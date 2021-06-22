package com.mrh0.createaddition.rendering;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import com.mrh0.createaddition.energy.IWireNode;
import com.mrh0.createaddition.energy.WireType;

import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.vector.Matrix4f;
import net.minecraft.util.math.vector.Vector3f;

public class WireNodeRenderer<T extends TileEntity> extends TileEntityRenderer<T> {

	public WireNodeRenderer(TileEntityRendererDispatcher rendererDispatcherIn) {
		super(rendererDispatcherIn);
	}
	
	private static final float HANG = 0.5f;
	
	@Override
	public void render(T tileEntityIn, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int combinedLightIn, int combinedOverlayIn) {
		IWireNode te = (IWireNode) tileEntityIn;
		for(int i = 0; i < te.getNodeCount(); i++) {
			if(te.getNodeType(i) != null) {
				Vector3f d1 = te.getNodeOffset(i);
				float ox1 = ((float)d1.getX());
				float oy1 = ((float)d1.getY());
				float oz1 = ((float)d1.getZ());
				
				IWireNode wn = te.getNode(i);
				if(wn == null) 
					return;
				
				Vector3f d2 = wn.getNodeOffset(te.getOtherNodeIndex(i)); // get other
				float ox2 = ((float)d2.getX());
				float oy2 = ((float)d2.getY());
				float oz2 = ((float)d2.getZ());
				
				float tx = te.getNodePos(i).getX() - te.getMyPos().getX();
		        float ty = te.getNodePos(i).getY() - te.getMyPos().getY();
		        float tz = te.getNodePos(i).getZ() - te.getMyPos().getZ();
				
				matrixStackIn.push();
				
				IVertexBuilder ivertexbuilder1 = bufferIn.getBuffer(RenderType.getLines());
				Matrix4f matrix4f1 = matrixStackIn.peek().getModel();
				
				WireType type = te.getNodeType(i);
				
				float dis = distanceFromZero(tx, ty, tz);
				
				if(ty+(oy2-oy1) < 0) {
					matrixStackIn.translate(tx+.5f + ox2, ty+.25f + oy2, tz+.5f + oz2);
					
					for(int k = 0; k < 16; ++k) {
						vert(-tx - ox2 + ox1, -ty - oy2 + oy1, -tz - oz2 + oz1, ivertexbuilder1, matrix4f1, divf(k, 16), type, dis);
						vert(-tx - ox2 + ox1, -ty - oy2 + oy1, -tz - oz2 + oz1, ivertexbuilder1, matrix4f1, divf(k + 1, 16), type, dis);//-tx, -ty, -tz,
					}
		        }
				else {
					matrixStackIn.translate(.5f + ox1, .25f + oy1, .5f + oz1);
					
					for(int k = 0; k < 16; ++k) {
						vert(tx - ox1 + ox2, ty - oy1 + oy2, tz - oz1 + oz2, ivertexbuilder1, matrix4f1, divf(k, 16), type, dis);
						vert(tx - ox1 + ox2, ty - oy1 + oy2, tz - oz1 + oz2, ivertexbuilder1, matrix4f1, divf(k + 1, 16), type, dis);//tx, ty, tz,
					}
				}
				
				matrixStackIn.pop();
			}
		}
	}
	
	private static float divf(int a, int b) {
		return (float)a / (float)b;
	}
	
	private static void vert(float x, float y, float z, IVertexBuilder builder, Matrix4f matrix, float f, WireType type, float dis) {
		builder.vertex(matrix, x * f, y * (f * f + f) * 0.5F + 0.25F + hang(f, dis), z * f).color(type.getRed(), type.getGreen(), type.getBlue(), 255).endVertex();
	}
	
	private static float hang(float f, float dis) {
		return (float)Math.sin(-f * (float)Math.PI) * (HANG * dis / (float)IWireNode.MAX_LENGTH);
	}
	
	public float distanceFromZero(float x, float y, float z) {
	    return (float)Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2) + Math.pow(z, 2));
	}
}

