package corner.z7.SevenZip.Archive.SevenZip;

import corner.z7.Common.RecordVector;

import corner.z7.SevenZip.Archive.Common.BindInfo;


class BindInfoEx extends BindInfo {
    RecordVector<MethodID> CoderMethodIDs = new RecordVector<MethodID>();

    public void Clear() {
        super.Clear(); // CBindInfo::Clear();
        CoderMethodIDs.clear();
    }
}
