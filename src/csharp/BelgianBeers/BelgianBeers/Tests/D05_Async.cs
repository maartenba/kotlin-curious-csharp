using System;
using System.Collections.Generic;
using System.Diagnostics;
using System.Linq;
using System.Threading.Tasks;
using BelgianBeers.Models;
using Xunit;
using Xunit.Abstractions;
using static BelgianBeers.Tests.Utilities.TestData;

namespace BelgianBeers.Tests
{
    [Collection("Demo 5 - Async")]
    public class D05_Async
    {
        private readonly Random _random = new Random();
        private readonly ITestOutputHelper _outputHelper;

        public D05_Async(ITestOutputHelper outputHelper)
        {
            _outputHelper = outputHelper;
        }

        [Fact]
        public async Task FetchStrongestBeers()
        {
            // Create tasks
            var tasks = new List<Task<(Beer beer, double strongness)>>();
            
            await LogElapsedTime(async () =>
            {
                foreach (var beer in BeerFlow)
                {
                    var task = Task.Factory.StartNew(async () =>
                    {
                        var strongness = await RetrieveBeerStrongness(beer);
                        
                        return (beer, strongness);
                    });
                    
                    tasks.Add(task.Unwrap());
                }

                await Task.WhenAll(tasks);
            });

            // Get strongest beers
            foreach (var (beer, strongness) in tasks
                 .Where(t => t.IsCompletedSuccessfully)
                .Select(t => t.Result)
                .OrderByDescending(r => r.strongness).Take(10))
            {
                _outputHelper.WriteLine($"{strongness:P} - {beer.Name}");
            }
        }

        private async Task<double> RetrieveBeerStrongness(Beer beer)
        {
            await Task.Delay(_random.Next(10, 300));

            return _random.NextDouble() * _random.NextDouble() * 0.2 / 2;
        }
    }
}