using System.Linq;
using System.Threading.Tasks;
using BelgianBeers.Repositories;
using BelgianBeers.Tests.Utilities;
using Xunit;

namespace BelgianBeers.Tests
{
    public class D01_LoadJson
    {
        [Fact]
        public void LoadsDataFromJsonFile()
        {
            var sourceData = TestData.DetermineDataPath("beerswithnulls.json");
            var repository = BeersRepository.FromFile(sourceData);
            
            Assert.True(repository.GetBeers().Any());
        }
    }
}