package com.league.blockchain.block;

import java.util.List;

/**
 *  区块body, 里面存放交易的数组
 */
public class BlockBody {
    private List<Instruction> instructions;

    @Override
    public String toString() {
        return "BlockBody{" +
                "instructions=" + instructions +
                '}';
    }

    public List<Instruction> getInstructions() {
        return instructions;
    }

    public void setInstructions(List<Instruction> instructions) {
        this.instructions = instructions;
    }
}
