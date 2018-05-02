using System;
using System.Linq;
using BelgianBeers.Models;
using BelgianBeers.Tests.Utilities;
using Xunit;
using Xunit.Abstractions;

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
            // TODO DEMO: Needs null check - Brewery property can be null (use annotation so IDE warns us)
            var westmalleBeers = TestData.BeerFlow
                .Where(beer => string.Equals(beer.Brewery.Name, "Brouwerij der Trappisten van Westmalle", StringComparison.OrdinalIgnoreCase))
                .ToList();
            
            // Pattern matching (on a property, not on type):
            foreach (var westmalleBeer in westmalleBeers)
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
        
    }
}