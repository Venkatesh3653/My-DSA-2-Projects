public class LSMTreeCompaction {

    public static void main(String[] args) {

        double memtableSizeGB = 1.0;
        int level0Tables = 8;

        double logicalDataWritten = level0Tables * memtableSizeGB;

        double dataReadForCompaction = logicalDataWritten;
        double dataWrittenToLevel1 = logicalDataWritten;

        double totalPhysicalWrites =
                logicalDataWritten + dataWrittenToLevel1;

        double writeAmplification =
                totalPhysicalWrites / logicalDataWritten;

        System.out.println("Logical Data Ingested: "
                + logicalDataWritten + " GB");

        System.out.println("Level-1 SSTable Size: "
                + logicalDataWritten + " GB");

        System.out.println("Total Physical Writes: "
                + totalPhysicalWrites + " GB");

        System.out.println("Write Amplification: "
                + writeAmplification + "x");
    }
}
