using System.Linq;
using BelgianBeers.Models;
using BelgianBeers.Repositories;
using BelgianBeers.Tests.Utilities;
using JetBrains.Annotations;
using Xunit;

namespace BelgianBeers.Tests
{
    [Collection("Demo 1 - Load JSON")]
    public class D01_LoadJson_Approach1
    {
        [Fact]
        public void LoadsDataFromJsonFile()
        {
            var sourceData = TestData.DetermineDataPath("beerswithnulls.json");
            var repository = CreateRepositoryFromFile(sourceData);

            Assert.True(repository.GetBeers().Any());
        }

        private static BeersRepository CreateRepositoryFromFile([PathReference] string file)
        {
            var repository = new BeersRepository();

            foreach (var (beerName, breweryName, rating, votes) in BeersStream.FromFile(file))
            {
                // TODO DEMO: The get/add pattern is stupid! Add GetHashCode to Brewery() and just call .Add on the underlying hashset

                // Store the brewery
                var brewery = repository.GetBrewery(breweryName);
                if (brewery == null)
                {
                    brewery = new Brewery(breweryName);
                    repository.AddBrewery(brewery);
                }

                // Store the beer
                var beer = repository.GetBeer(beerName);
                if (beer == null)
                {
                    beer = new Beer(beerName, brewery, rating, votes);
                    repository.AddBeer(beer);
                }
            }


            return repository;
        }
    }
}