package org.jpc.emulator.execution.opcodes.vm;

import org.jpc.emulator.execution.*;
import org.jpc.emulator.execution.decoder.*;
import org.jpc.emulator.processor.*;
import org.jpc.emulator.processor.fpu64.*;
import static org.jpc.emulator.processor.Processor.*;

public class lodsd_a16 extends Executable
{
    final int segIndex;

    public lodsd_a16(int blockStart, Instruction parent)
    {
        super(blockStart, parent);
        segIndex = Processor.getSegmentIndex(parent.getSegment());
    }

    public Branch execute(Processor cpu)
    {
        Segment seg = cpu.segs[segIndex];
        StaticOpcodes.lodsd_a16(cpu, seg);
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