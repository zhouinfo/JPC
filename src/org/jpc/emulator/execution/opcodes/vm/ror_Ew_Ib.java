package org.jpc.emulator.execution.opcodes.vm;

import org.jpc.emulator.execution.*;
import org.jpc.emulator.execution.decoder.*;
import org.jpc.emulator.processor.*;
import org.jpc.emulator.processor.fpu64.*;
import static org.jpc.emulator.processor.Processor.*;

public class ror_Ew_Ib extends Executable
{
    final int op1Index;
    final int immb;

    public ror_Ew_Ib(int blockStart, int eip, int prefices, PeekableInputStream input)
    {
        super(blockStart, eip);
        int modrm = input.readU8();
        op1Index = Modrm.Ew(modrm);
        immb = Modrm.Ib(input);
    }

    public Branch execute(Processor cpu)
    {
        Reg op1 = cpu.regs[op1Index];
            int shift = immb & (16-1);
            int reg0 = 0xFFFF&op1.get16();
            int res = (reg0 >>> shift) | (reg0 << (16 - shift));
            op1.set16((short)res);
            boolean bit30  = (res & (1 << (16-2))) != 0;
            boolean bit31 = (res & (1 << (16-1))) != 0;
            if (immb > 0)
            {
                cpu.cf = bit31;
                if (immb == 1)
                {
                    cpu.of = bit30 ^ bit31;
                    cpu.flagStatus &= NOFCF;
                }
                else
                    cpu.flagStatus &= NCF;
            }
        return Branch.None;
    }

    public boolean isBranch()
    {
        return false;
    }

    public String toString()
    {
        return this.getClass().getName();
    }
}