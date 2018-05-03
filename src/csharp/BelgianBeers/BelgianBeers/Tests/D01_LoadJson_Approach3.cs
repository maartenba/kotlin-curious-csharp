using System.Linq;
using BelgianBeers.Models;
using BelgianBeers.Repositories;
using BelgianBeers.Tests.Utilities;
using JetBrains.Annotations;
using Xunit;

namespace BelgianBeers.Tests
{
    [Collection("Demo 1 - Load JSON")]
    public class D01_LoadJson_Approach3
    {
        [Fact]
        public void LoadsDataFromJsonFile()
        {
            var repository = new BeersRepository();

            foreach (var (beerName, breweryName, rating, votes) in TestData.Beers)
            {
                // Store the brewery
                // TODO DEMO: Null checks here are still a bit ugly
                var brewery = !string.IsNullOrEmpty(breweryName)
                    ? new Brewery(breweryName)
                    : null;
                repository.AddBrewery(brewery);

                // Store the beer
                var beer = new Beer(beerName, brewery, rating, votes);
                repository.AddBeer(beer);
            }

            Assert.True(repository.GetBeers().Any());
        }
    }
}
