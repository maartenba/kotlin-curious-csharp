using System.Linq;
using BelgianBeers.Models;
using BelgianBeers.Repositories;
using BelgianBeers.Tests.Utilities;
using JetBrains.Annotations;
using Xunit;

namespace BelgianBeers.Tests
{
    [Collection("Demo 1 - Load JSON")]
    public class D01_LoadJson_Approach2
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
                // Store the brewery
                // TODO DEMO: Null checks here are ugly, quick. Does Kotlin have anything better?
                Brewery brewery =  null;
                if (!string.IsNullOrEmpty(breweryName))
                {
                    brewery = repository.GetBrewery(breweryName);
                    if (brewery == null)
                    {
                        brewery = new Brewery(breweryName);
                        repository.AddBrewery(brewery);
                    }
                }
                    
                // Store the beer
                // TODO DEMO: This get/add is needed to ensure no duplicates, however we could again do a GetHashCode() instead
                var beer = repository.GetBeer(beerName);
                if (beer == null)
                {
                    beer = new Beer(beerName, brewery, rating, votes);
                    repository.AddBeer(beer);
                }
            };
                
                
            return repository;
        }
    }
}