package corner.z7.SevenZip.Archive.SevenZip;

import corner.z7.Common.ObjectVector;


class CoderInfo {
    int NumInStreams;
    int NumOutStreams;
    public ObjectVector<AltCoderInfo> AltCoders = new corner.z7.Common.ObjectVector<AltCoderInfo>();

    public CoderInfo() {
        NumInStreams = 0;
        NumOutStreams = 0;
    }

    boolean IsSimpleCoder() {
        return (NumInStreams == 1) && (NumOutStreams == 1);
    }
}
