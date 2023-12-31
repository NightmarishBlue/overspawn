package blue.nightmarish.overspawn;

import blue.nightmarish.overspawn.config.OverspawnCommonConfig;
import blue.nightmarish.overspawn.config.OverspawnWorldConfig;
import com.mojang.logging.LogUtils;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.util.ObfuscationReflectionHelper;
import org.slf4j.Logger;

import java.lang.reflect.Field;

@Mod(Overspawn.MOD_ID)
public class Overspawn
{
    // Define mod id in a common place for everything to reference
    public static final String MOD_ID = "overspawn";
    // Directly reference a slf4j logger
    private static final Logger LOGGER = LogUtils.getLogger();
    // Create a Deferred Register to hold Blocks which will all be registered under the "overspawn" namespace
//    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, MOD_ID);
//    // Create a Deferred Register to hold Items which will all be registered under the "overspawn" namespace
//    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MOD_ID);

    // Creates a new Block with the id "overspawn:example_block", combining the namespace and path
//    public static final RegistryObject<Block> EXAMPLE_BLOCK = BLOCKS.register("example_block", () -> new Block(BlockBehaviour.Properties.of(Material.STONE)));
//    // Creates a new BlockItem with the id "overspawn:example_block", combining the namespace and path
//    public static final RegistryObject<Item> EXAMPLE_BLOCK_ITEM = ITEMS.register("example_block", () -> new BlockItem(EXAMPLE_BLOCK.get(), new Item.Properties().tab(CreativeModeTab.TAB_BUILDING_BLOCKS)));

    public Overspawn()
    {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        // Register the commonSetup method for modloading
        modEventBus.addListener(this::commonSetup);

        // Register the Deferred Register to the mod event bus so blocks get registered
//        BLOCKS.register(modEventBus);
//        // Register the Deferred Register to the mod event bus so items get registered
//        ITEMS.register(modEventBus);

        // Register the mod config
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, OverspawnCommonConfig.SPEC, MOD_ID + "-common.toml");
        ModLoadingContext.get().registerConfig(ModConfig.Type.SERVER, OverspawnWorldConfig.SPEC, MOD_ID + "-world.toml");

        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);
    }

    private void commonSetup(final FMLCommonSetupEvent event)
    {
        // Some common setup code
//        LOGGER.info("HELLO FROM COMMON SETUP");
//        LOGGER.info("DIRT BLOCK >> {}", ForgeRegistries.BLOCKS.getKey(Blocks.DIRT));
    }

    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) throws IllegalAccessException {
        // reflect the field. gonna have to do something about the field name.
        Field creatureField = ObfuscationReflectionHelper.findField(MobCategory.class, "f_21586_"); // this field is the "max" field of MobCategory
        int globalCap = OverspawnCommonConfig.DEFAULT_CREATURE_SPAWN_CAP.get();
        int worldCap = OverspawnWorldConfig.CREATURE_SPAWN_CAP.get();
        if (worldCap == 0) worldCap = globalCap; // if the world has no cap configured, set it to the common
        creatureField.set(MobCategory.CREATURE, worldCap);
        LOGGER.info("the creature spawn cap is " + worldCap);
    }

    // You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
    @Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents
    {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event)
        {
            // Some client setup code
//            LOGGER.info("HELLO FROM CLIENT SETUP");
//            LOGGER.info("MINECRAFT NAME >> {}", Minecraft.getInstance().getUser().getName());
        }
    }
}
