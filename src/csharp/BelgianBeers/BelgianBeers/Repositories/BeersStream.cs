using System;
using System.Collections.Generic;
using System.IO;
using System.Threading.Tasks;
using BelgianBeers.Models;
using JetBrains.Annotations;
using Newtonsoft.Json;
using Newtonsoft.Json.Linq;

namespace BelgianBeers.Repositories
{
    public static class BeersStream
    {
        public static async void FromFile([PathReference] string file, Action<Beer> callback)
        {
            if (!File.Exists(file))
            {
                throw new FileNotFoundException("Data file not found.", file);
            }

            using (var reader = new JsonTextReader(new StreamReader(File.OpenRead(file))))
            {
                while (await reader.ReadAsync())
                {
                    if (reader.TokenType == JsonToken.StartObject)
                    {
                        // Load data from the stream
                        var beerData = JObject.Load(reader);

                        var breweryName = beerData.Value<string>("brewery");
                        var beerName = beerData.Value<string>("name");
                        var rating = beerData.Value<double>("rating");
                        var votes = beerData.Value<double>("votes");

                        var beer = new Beer(beerName, new Brewery(breweryName), rating, votes);
                        callback(beer);
                    }
                }
            }
        }
    }
}
