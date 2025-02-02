package gregtech.api.items.metaitem;

import gregtech.api.capability.impl.SimpleThermalFluidHandlerItemStack;
import gregtech.api.capability.impl.ThermalFluidHandlerItemStack;
import gregtech.api.items.metaitem.stats.IItemCapabilityProvider;
import gregtech.api.items.metaitem.stats.IItemComponent;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.fluids.FluidStack;

import java.util.function.Function;

public class FilteredFluidStats implements IItemComponent, IItemCapabilityProvider {

    public final int capacity;
    public final boolean allowPartialFill;
    private final Function<FluidStack, Boolean> fillPredicate;

    public FilteredFluidStats(int capacity, boolean allowPartialFill, Function<FluidStack, Boolean> fillPredicate) {
        this.capacity = capacity;
        this.allowPartialFill = allowPartialFill;
        this.fillPredicate = fillPredicate;
    }

    @Override
    public ICapabilityProvider createProvider(ItemStack itemStack) {
        if (allowPartialFill) {
            return new ThermalFluidHandlerItemStack(itemStack, capacity, Integer.MAX_VALUE, true, true, true, true) {
                @Override
                public boolean canFillFluidType(FluidStack fluid) {
                    return super.canFillFluidType(fluid) && fillPredicate.apply(fluid);
                }
            };
        }
        return new SimpleThermalFluidHandlerItemStack(itemStack, capacity, Integer.MAX_VALUE, true, true, true, true) {
            @Override
            public boolean canFillFluidType(FluidStack fluid) {
                return super.canFillFluidType(fluid) && fillPredicate.apply(fluid);
            }
        };
    }
}
