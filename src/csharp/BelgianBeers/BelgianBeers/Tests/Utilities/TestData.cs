using System.IO;

namespace BelgianBeers.Tests.Utilities
{
    public class TestData
    {
        public static string DetermineDataPath(string fileName, string basePath = null)
        {
            if (basePath == null) basePath = Path.GetFullPath(Directory.GetCurrentDirectory());
            var data = Path.Combine(basePath, "data", fileName);

            if (File.Exists(data)) return data;

            return DetermineDataPath(
                basePath: Path.Combine(basePath, ".."),
                fileName: fileName
            );
        }
    }
}