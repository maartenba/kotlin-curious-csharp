using System;
using System.Linq;
using System.Threading.Tasks;
using BelgianBeers.Models;
using BelgianBeers.Repositories;
using BelgianBeers.Tests.Utilities;
using Xunit;
using Xunit.Abstractions;

namespace BelgianBeers.Tests
{
    public class D02_Filtering
    {
        private readonly ITestOutputHelper _outputHelper;

        public D02_Filtering(ITestOutputHelper outputHelper)
        {
            _outputHelper = outputHelper;
        }
        
        [Fact]
        public async Task LinqDsl()
        {
            var sourceData = TestData.DetermineDataPath("beerswithnulls.json");
            var repository = await BeersRepository.FromFile(sourceData);
            
            // Filtering data with a DSL - Get beers with a rating > .50, and at least 10 votes for relevance
            var beersWithOkayRating = from beer in repository.GetBeers()
                where beer.Rating > .50 && beer.Votes >= 10
                select beer;
            
            Assert.True(beersWithOkayRating.Any());
        }
        
        [Fact]
        public async Task LinqMethods()
        {
            var sourceData = TestData.DetermineDataPath("beerswithnulls.json");
            var repository = await BeersRepository.FromFile(sourceData);
            
            // Filtering data with LINQ method chains a DSL - Get beers with a rating > .50, and at least 10 votes for relevance
            var beersWithOkayRating = repository.GetBeers()
                .Where(beer => beer.Rating > .50 && beer.Votes >= 10)
                .ToList();
            
            // TODO DEMO: So many allocations - check in IL, mention https://github.com/antiufo/roslyn-linq-rewrite
            
            Assert.True(beersWithOkayRating.Any());
        }
        
        [Fact]
        public async Task PatternMatching()
        {
            var sourceData = TestData.DetermineDataPath("beerswithnulls.json");
            var repository = await BeersRepository.FromFile(sourceData);
            
            // Get beers that are from brewery "Westmalle"
            // TODO DEMO: Needs null check - Brewery property can be null (use annotation so IDE warns us)
            var westmalleBeers = repository.GetBeers()
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
        
        [Fact]
        public async Task Statistics()
        {
            var sourceData = TestData.DetermineDataPath("beerswithnulls.json");
            var repository = await BeersRepository.FromFile(sourceData);
            
            // Statistics:

            var topRatedBreweries = from beer in repository.GetBeers()
                where beer.Brewery != null
                group beer by beer.Brewery into beersPerBrewery
                orderby beersPerBrewery.Average(beer => beer.Rating) descending
                select beersPerBrewery.Key;

            foreach (var brewery in topRatedBreweries.Take(10))
            {
                _outputHelper.WriteLine(brewery.Name);
            }
            
            Assert.True(topRatedBreweries.Any());
        }
    }
}