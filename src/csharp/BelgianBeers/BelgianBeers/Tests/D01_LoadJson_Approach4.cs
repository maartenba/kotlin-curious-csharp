using System.Collections.Generic;
using System.Linq;
using BelgianBeers.Models;
using BelgianBeers.Tests.Utilities;
using Xunit;

namespace BelgianBeers.Tests
{
    [Collection("Demo 1 - Load JSON")]
    public class D01_LoadJson_Approach4
    {
        [Fact]
        public void LoadsDataFromJsonFile()
        {
            var _breweries = new HashSet<Brewery>();
            var _beers = new HashSet<Beer>();

            foreach (var (beerName, breweryName, rating, votes) in TestData.Beers)
            {
                // Store the brewery
                // TODO DEMO: Null checks here are still a bit ugly
                var brewery = !string.IsNullOrEmpty(breweryName)
                    ? new Brewery(breweryName)
                    : null;
                
                if (brewery != null)
                {
                    _breweries.Add(brewery);
                }

                // Store the beer
                var beer = new Beer(beerName, brewery, rating, votes);
                _beers.Add(beer);
            }

            Assert.True(_beers.Any());
        }
    }
}
