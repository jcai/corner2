package corner.z7.SevenZip.Archive.SevenZip;

import corner.z7.Common.BoolVector;
import corner.z7.Common.IntVector;
import corner.z7.Common.LongVector;
import corner.z7.Common.ObjectVector;
import corner.z7.Common.RecordVector;


class ArchiveDatabase {
    public LongVector PackSizes = new LongVector();
    public BoolVector PackCRCsDefined = new BoolVector();
    public IntVector PackCRCs = new IntVector();
    public ObjectVector<Folder> Folders = new ObjectVector<Folder>();
    public IntVector NumUnPackStreamsVector = new IntVector();
    public ObjectVector<FileItem> Files = new ObjectVector<FileItem>();

    void Clear() {
        PackSizes.clear();
        PackCRCsDefined.clear();
        PackCRCs.clear();
        Folders.clear();
        NumUnPackStreamsVector.clear();
        Files.clear();
    }
}
