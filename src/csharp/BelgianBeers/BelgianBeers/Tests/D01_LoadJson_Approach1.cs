using System.Linq;
using BelgianBeers.Models;
using BelgianBeers.Repositories;
using BelgianBeers.Tests.Utilities;
using Xunit;

namespace BelgianBeers.Tests
{
    [Collection("Demo 1 - Load JSON")]
    public class D01_LoadJson_Approach1
    {
        [Fact]
        public void LoadsDataFromJsonFile()
        {
            var repository = new BeersRepository();

            foreach (var (beerName, breweryName, rating, votes) in TestData.Beers)
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

            Assert.True(repository.GetBeers().Any());
        }
    }
}
