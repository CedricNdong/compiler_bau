package de.thm.mni.compilerbau.phases._05_varalloc;

import de.thm.mni.compilerbau.utils.NotImplemented;

/**
 * This class describes the stack frame layout of a procedure.
 * It contains the sizes of the various subareas and provides methods to retrieve information about the stack frame required to generate code for the procedure.
 */
public class StackLayout {
    // The following values have to be set in phase 5
    public Integer argumentAreaSize = null;
    public Integer localVarAreaSize = null;
    public Integer outgoingAreaSize = null;

    /**
     * A leaf procedure is a procedure that does not call any other procedure in its body.
     *
     * @return whether the procedure this stack layout describes is a leaf procedure.
     */
    public boolean isLeafProcedure() {
        //TODO (assignment 5): Think about how to encode the absence of calls and use this to implement this method

        throw new NotImplemented();
    }

    /**
     * @return The total size of the stack frame described by this object.
     */
    public int frameSize() {
        //TODO (assignment 5): Calculate the size of the stack frame

        throw new NotImplemented();
    }

    /**
     * @return The offset (starting from the new stack pointer) where the old frame pointer is stored in this stack frame.
     */
    public int oldFramePointerOffset() {
        //TODO (assignment 5): Calculate the offset of the old frame pointer

        throw new NotImplemented();
    }

    /**
     * @return The offset (starting from the new frame pointer) where the old return adress is stored in this stack frame.
     */
    public int oldReturnAddressOffset() {
        //TODO (assignment 5): Calculate the offset of the old return address

        throw new NotImplemented();
    }
}
