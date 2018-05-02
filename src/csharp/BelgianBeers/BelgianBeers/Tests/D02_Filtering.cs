using System.Linq;
using BelgianBeers.Tests.Utilities;
using Xunit;

namespace BelgianBeers.Tests
{
    [Collection("Demo 2 - Filtering")]
    public class D02_Filtering
    {
        [Fact]
        public void LinqDsl()
        {
            // Filtering data with a DSL - Get beers with a rating > .50, and at least 10 votes for relevance
            var beersWithOkayRating = from beer in TestData.BeerFlow
                where beer.Rating > .50 && beer.Votes >= 10
                select beer;

            Assert.True(beersWithOkayRating.Any());
        }

        [Fact]
        public void LinqMethods()
        {
            // Filtering data with LINQ method chains a DSL - Get beers with a rating > .50, and at least 10 votes for relevance
            var beersWithOkayRating = TestData.BeerFlow
                .Where(beer => beer.Rating > .50 && beer.Votes >= 10)
                .ToList();

            // TODO DEMO: So many allocations - check in IL, mention https://github.com/antiufo/roslyn-linq-rewrite

            Assert.True(beersWithOkayRating.Any());
        }
    }
}
