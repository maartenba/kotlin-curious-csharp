using System;
using System.Linq;
using Xunit;
using static BelgianBeers.Tests.Utilities.TestData;

namespace BelgianBeers.Tests
{
    [Collection("Demo 2 - Filtering")]
    public class D02_Filtering
    {
        [Fact]
        public void LinqDsl()
        {
            var minRating = .50;
            var minVotes = 10;
            
            // Filtering data with a DSL - Get beers with a rating > .50, and at least 10 votes for relevance
            var beersWithOkayRating = from beer in BeerFlow
                where beer.Rating > minRating && beer.Votes >= minVotes
                select beer;

            Assert.True(beersWithOkayRating.Any());
        }

        [Fact]
        public void LinqMethods()
        {
            var minRating = .50;
            var minVotes = 10;
            
            // Filtering data with LINQ method chains a DSL - Get beers with a rating > .50, and at least 10 votes for relevance
            var beersWithOkayRating = BeerFlow
                .Where(beer => beer.Rating > minRating && beer.Votes >= minVotes)
                .ToList();

            // TODO DEMO: So many allocations - check in IL, mention https://github.com/antiufo/roslyn-linq-rewrite

            Assert.True(beersWithOkayRating.Any());
        }

        [Fact]
        public void SideNote_Nullability()
        {
            // TODO DEMO: Needs null check - Brewery property can be null (use annotation so IDE warns us)

            // C# 8.0 will have nullable reference types - https://github.com/dotnet/csharplang/blob/master/proposals/nullable-reference-types.md
            
            var westmalleBeers = BeerFlow
                .Where(beer => string.Equals(beer.Brewery.Name, "Brouwerij der Trappisten van Westmalle", StringComparison.OrdinalIgnoreCase))
                .ToList();
            
            Assert.True(westmalleBeers.Any());
        }
    }
}
