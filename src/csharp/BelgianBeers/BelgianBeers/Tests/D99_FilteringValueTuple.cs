using System.Linq;
using BelgianBeers.Models;
using BelgianBeers.Repositories;
using BelgianBeers.Tests.Utilities;
using Xunit;

namespace BelgianBeers.Tests
{
    public class D99_FilteringValueTuple
    {
        [Fact]
        public void LinqMethodsOnStreamOfValueTuple()
        {
            var sourceData = TestData.DetermineDataPath("beerswithnulls.json");
            
            // Filtering data with LINQ method chains a DSL - Get beers with a rating > .50, and at least 10 votes for relevance
            var beersWithOkayRating = BeersStream.FromFile(sourceData)
                .Where(tuple => tuple.rating > .50 && tuple.votes >= 10)
                .Select(tuple => new Beer(tuple.beerName, new Brewery(tuple.breweryName), tuple.rating, tuple.votes))
                .ToList();
            
            Assert.True(beersWithOkayRating.Any());
        }
        
        [Fact]
        public void LinqMethodsOnStreamOfBeer()
        {
            var sourceData = TestData.DetermineDataPath("beerswithnulls.json");
            
            // Filtering data with LINQ method chains a DSL - Get beers with a rating > .50, and at least 10 votes for relevance
            var beersWithOkayRating = BeersStream.FromFileAsBeers(sourceData)
                .Where(beer => beer.Rating > .50 && beer.Votes >= 10)
                .ToList();
            
            Assert.True(beersWithOkayRating.Any());
        }
    }
}