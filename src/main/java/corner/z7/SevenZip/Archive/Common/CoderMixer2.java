package corner.z7.SevenZip.Archive.Common;

import corner.z7.Common.LongVector;


public interface CoderMixer2 {
    void ReInit();

    void SetBindInfo(BindInfo bindInfo);

    void SetCoderInfo(int coderIndex, LongVector inSizes, LongVector outSizes);
}
