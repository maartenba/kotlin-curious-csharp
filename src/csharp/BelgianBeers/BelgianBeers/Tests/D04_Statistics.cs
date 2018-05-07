using System.Linq;
using Xunit;
using Xunit.Abstractions;
using static BelgianBeers.Tests.Utilities.TestData;

namespace BelgianBeers.Tests
{
    [Collection("Demo 4 - Statistics")]
    public class D04_Statistics
    {
        private readonly ITestOutputHelper _outputHelper;

        public D04_Statistics(ITestOutputHelper outputHelper)
        {
            _outputHelper = outputHelper;
        }

        [Fact]
        public void Statistics()
        {
            // Statistics:
            var topRatedBreweries = from beer in BeerFlow
                where beer.Brewery != null
                group beer by beer.Brewery into beersPerBrewery
                let average = beersPerBrewery.Average(beer => beer.Rating)
                orderby average descending
                select (brewery: beersPerBrewery.Key, average);

            foreach (var result in topRatedBreweries.Take(10))
            {
                _outputHelper.WriteLine($"{result.average:P} - {result.brewery.Name}");
            }
        }
    }
}