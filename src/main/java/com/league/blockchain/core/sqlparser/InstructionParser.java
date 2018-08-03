package com.league.blockchain.core.sqlparser;

import com.league.blockchain.block.InstructionBase;

public interface InstructionParser {
    boolean parse(InstructionBase instructionBase);
}
