using System.Linq;
using BelgianBeers.Models;
using Xunit;
using static BelgianBeers.Tests.Utilities.TestData;

namespace BelgianBeers.Tests
{
    [Collection("Not part of demo")]
    public class D99_FilteringValueTuple
    {
        [Fact]
        public void LinqMethodsOnStreamOfValueTuple()
        {
            // Filtering data with LINQ method chains a DSL - Get beers with a rating > .50, and at least 10 votes for relevance
            var beersWithOkayRating = Beers
                .Where(tuple => tuple.rating > .50 && tuple.votes >= 10)
                .Select(tuple => new Beer(tuple.beerName, new Brewery(tuple.breweryName), tuple.rating, tuple.votes))
                .ToList();
            
            Assert.True(beersWithOkayRating.Any());
        }
        
        [Fact]
        public void LinqMethodsOnStreamOfBeer()
        {
            // Filtering data with LINQ method chains a DSL - Get beers with a rating > .50, and at least 10 votes for relevance
            var beersWithOkayRating = BeerFlow
                .Where(beer => beer.Rating > .50 && beer.Votes >= 10)
                .ToList();
            
            Assert.True(beersWithOkayRating.Any());
        }
    }
}
