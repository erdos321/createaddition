package com.mrh0.createaddition.index;

import com.mrh0.createaddition.CreateAddition;
import com.mrh0.createaddition.entities.overcharged_hammer.OverchargedHammerEntity;
import com.mrh0.createaddition.entities.overcharged_hammer.OverchargedHammerRenderer;
import com.simibubi.create.Create;
import com.simibubi.create.content.contraptions.components.structureMovement.glue.SuperGlueEntity;
import com.simibubi.create.foundation.data.CreateEntityBuilder;
import com.simibubi.create.foundation.data.CreateRegistrate;
import com.simibubi.create.foundation.utility.Lang;
import com.simibubi.create.repack.registrate.util.entry.EntityEntry;
import com.simibubi.create.repack.registrate.util.entry.RegistryEntry;

import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EntityType.Builder;
import net.minecraft.world.entity.EntityType.EntityFactory;
import net.minecraft.world.entity.MobCategory;
import com.simibubi.create.repack.registrate.util.nullness.NonNullConsumer;
import com.simibubi.create.repack.registrate.util.nullness.NonNullFunction;
import com.simibubi.create.repack.registrate.util.nullness.NonNullSupplier;

public class CAEntities {
	private static final CreateRegistrate REGISTRATE = CreateAddition.registrate();
	
	/*public static final EntityEntry<OverchargedHammerEntity> OVERCHARGED_HAMMER_ENTITY = 
			register("overcharged_hammer", OverchargedHammerEntity::new, () -> OverchargedHammerRenderer::new, MobCategory.MISC, 10, 40, false);*/
	
	public static final EntityEntry<OverchargedHammerEntity> OVERCHARGED_HAMMER_ENTITY =
			register("super_glue", OverchargedHammerEntity::new, () -> OverchargedHammerRenderer::new, MobCategory.MISC, 10,
				40, true, true, OverchargedHammerEntity::build).register();
	
	/*public static <T extends Entity> RegistryEntry<EntityType<T>> register(String name, EntityFactory<T> factory, MobCategory group) {
		return REGISTRATE.entity(name, factory, group)
				.properties(b -> b.setTrackingRange(10)
					.setUpdateInterval(10)
					.setShouldReceiveVelocityUpdates(true))
				.properties(OverchargedHammerEntity::build)
				.properties(b -> {
					b.fireImmune();
				})
				.register();
	}*/
	
	public static void register() {}
	
	/*public static void registerRenderers() {
		RenderingRegistry.registerEntityRenderingHandler(OVERCHARGED_HAMMER_ENTITY.get(), OverchargedHammerRenderer::new);
	}*/
	
	private static <T extends Entity> CreateEntityBuilder<T, ?> register(String name, EntityFactory<T> factory,
			NonNullSupplier<NonNullFunction<Context, EntityRenderer<? super T>>> renderer,
			MobCategory group, int range, int updateFrequency, boolean sendVelocity, boolean immuneToFire,
			NonNullConsumer<Builder<T>> propertyBuilder) {
		String id = Lang.asId(name);
		return (CreateEntityBuilder<T, ?>) 
			REGISTRATE.entity(id, factory, group)
			.properties(b -> b.setTrackingRange(range)
				.setUpdateInterval(updateFrequency)
				.setShouldReceiveVelocityUpdates(sendVelocity))
			.properties(propertyBuilder)
			.properties(b -> {
				if (immuneToFire)
					b.fireImmune();
			})
			.renderer(renderer);
	}
}
