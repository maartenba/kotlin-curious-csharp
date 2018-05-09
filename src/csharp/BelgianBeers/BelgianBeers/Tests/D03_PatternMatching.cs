using System;
using System.Linq;
using BelgianBeers.Models;
using Xunit;
using Xunit.Abstractions;
using static BelgianBeers.Tests.Utilities.TestData;

namespace BelgianBeers.Tests
{
    [Collection("Demo 3 - Pattern Matching")]
    public class D03_PatternMatching
    {
        private readonly ITestOutputHelper _outputHelper;

        public D03_PatternMatching(ITestOutputHelper outputHelper)
        {
            _outputHelper = outputHelper;
        }
        
        [Fact]
        public void PatternMatching()
        {
            // Get beers that are from brewery "Westmalle"
            var westmalleBeers = BeerFlow
                .Where(beer => string.Equals(beer.Brewery?.Name, "Brouwerij der Trappisten van Westmalle", StringComparison.OrdinalIgnoreCase))
                .ToList();
            //https://github.com/dotnet/csharplang/blob/master/proposals/nullable-reference-types.md
            // Pattern matching (on a property, not on type):
            foreach (var westmalleBeer in westmalleBeers.Select(PatchBeer))
            {
                switch (westmalleBeer)
                {
                    case DubbelBeer dubbelBeer:
                        // It is a dubbel
                        _outputHelper.WriteLine(dubbelBeer.Name);
                        break;
                    
                    case TripelBeer tripelBeer when westmalleBeer.Name.IndexOf("tripel", StringComparison.OrdinalIgnoreCase) >= 0:
                        // It is a tripel
                        _outputHelper.WriteLine(tripelBeer.Name);
                        break;
                }
            }
            
            Assert.True(westmalleBeers.Any());
        }

        private Beer PatchBeer(Beer beer)
        {
            if (beer.Name.IndexOf("dubbel", StringComparison.OrdinalIgnoreCase) >= 0)
            {
                return new DubbelBeer(beer.Name, beer.Brewery, beer.Rating, beer.Votes);
            }
            
            if (beer.Name.IndexOf("tripel", StringComparison.OrdinalIgnoreCase) >= 0)
            {
                return new TripelBeer(beer.Name, beer.Brewery, beer.Rating, beer.Votes);
            }

            return beer;
        }
    }
}